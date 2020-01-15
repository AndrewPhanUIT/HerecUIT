
set -e

export MSYS_NO_PATHCONV=1
starttime=$(date +%s)
CC_SRC_LANGUAGE="java"

CC_RUNTIME_LANGUAGE=java
CC_SRC_PATH=/opt/gopath/src/HerecUIT/chaincode/java

# clean the keystore
rm -rf ./hfc-key-store

fabricNetworkDirPath=$1

if [ "${fabricNetworkDirPath}" != "" ]; then
  cd ${fabricNetworkDirPath}/fabric-network
  echo y | ${fabricNetworkDirPath}/fabric-network/herec.sh down
  echo y | ${fabricNetworkDirPath}/fabric-network/herec.sh up ${fabricNetworkDirPath}/fabric-network -a -n -s couchdb
else
  cd ./fabric-network
  echo y | ./herec.sh down
  echo y | ./herec.sh up -a -n -s couchdb
fi


export CHANNEL_NAME=herecchannel
export COMPOSE_PROJECT_NAME=fabric-network

CONFIG_ROOT=/opt/gopath/src/HerecUIT/hyperledger/fabric/peer
ORG1_MSPCONFIGPATH=${CONFIG_ROOT}/crypto/peerOrganizations/client.herec.uit/users/Admin@client.herec.uit/msp
ORG1_TLS_ROOTCERT_FILE=${CONFIG_ROOT}/crypto/peerOrganizations/client.herec.uit/peers/peer0.client.herec.uit/tls/ca.crt
ORG2_MSPCONFIGPATH=${CONFIG_ROOT}/crypto/peerOrganizations/quan12.herec.uit/users/Admin@quan12.herec.uit/msp
ORG2_TLS_ROOTCERT_FILE=${CONFIG_ROOT}/crypto/peerOrganizations/quan12.herec.uit/peers/peer0.quan12.herec.uit/tls/ca.crt
ORDERER_TLS_ROOTCERT_FILE=${CONFIG_ROOT}/crypto/ordererOrganizations/herec.uit/orderers/orderer.herec.uit/msp/tlscacerts/tlsca.herec.uit-cert.pem
set -x

echo "sleep 15s"
sleep 15

echo "Create new channel"
docker exec \
  -e "CORE_PEER_LOCALMSPID=ClientMSP" \
  -e "CORE_PEER_ADDRESS=peer0.client.herec.uit:7051" \
  -e "CORE_PEER_MSPCONFIGPATH=${ORG1_MSPCONFIGPATH}" \
  -e "CORE_PEER_TLS_ROOTCERT_FILE=${ORG1_TLS_ROOTCERT_FILE}" \
  cli \
  peer channel create \
    -o orderer.herec.uit:7050 \
    -c $CHANNEL_NAME \
    -f ./channel-artifacts/herecchannel.tx \
    --tls --cafile ${ORDERER_TLS_ROOTCERT_FILE} 

echo "Join Client to channel"
docker exec \
  -e "CORE_PEER_LOCALMSPID=ClientMSP" \
  -e "CORE_PEER_ADDRESS=peer0.client.herec.uit:7051" \
  -e "CORE_PEER_MSPCONFIGPATH=${ORG1_MSPCONFIGPATH}" \
  -e "CORE_PEER_TLS_ROOTCERT_FILE=${ORG1_TLS_ROOTCERT_FILE}" \
  cli \
  peer channel join \
    -b herecchannel.block

echo "Join Quan12 to channel"
docker exec \
  -e "CORE_PEER_LOCALMSPID=Quan12MSP" \
  -e "CORE_PEER_ADDRESS=peer0.quan12.herec.uit:9051" \
  -e "CORE_PEER_MSPCONFIGPATH=${ORG2_MSPCONFIGPATH}" \
  -e "CORE_PEER_TLS_ROOTCERT_FILE=${ORG2_TLS_ROOTCERT_FILE}" \
  cli \
  peer channel join \
    -b herecchannel.block

echo "Installing smart contract on peer0.client.herec.uit"
docker exec \
  -e CORE_PEER_LOCALMSPID=ClientMSP \
  -e CORE_PEER_ADDRESS=peer0.client.herec.uit:7051 \
  -e CORE_PEER_MSPCONFIGPATH=${ORG1_MSPCONFIGPATH} \
  -e CORE_PEER_TLS_ROOTCERT_FILE=${ORG1_TLS_ROOTCERT_FILE} \
  cli \
  peer chaincode install \
    -n diagnosis \
    -v 1.0 \
    -p "$CC_SRC_PATH" \
    -l "$CC_RUNTIME_LANGUAGE"

echo "Installing smart contract on peer0.quan12.herec.uit"
docker exec \
  -e "CORE_PEER_LOCALMSPID=Quan12MSP" \
  -e "CORE_PEER_ADDRESS=peer0.quan12.herec.uit:9051" \
  -e "CORE_PEER_MSPCONFIGPATH=${ORG2_MSPCONFIGPATH}" \
  -e "CORE_PEER_TLS_ROOTCERT_FILE=${ORG2_TLS_ROOTCERT_FILE}" \
  cli \
  peer chaincode install \
    -n diagnosis \
    -v 1.0 \
    -p "$CC_SRC_PATH" \
    -l "$CC_RUNTIME_LANGUAGE"

echo "Instantiating smart contract on herecchannel"
docker exec \
  -e CORE_PEER_LOCALMSPID=ClientMSP \
  -e CORE_PEER_ADDRESS=peer0.client.herec.uit:7051 \
  -e CORE_PEER_MSPCONFIGPATH=${ORG1_MSPCONFIGPATH} \
  -e CORE_PEER_TLS_ROOTCERT_FILE=${ORG1_TLS_ROOTCERT_FILE} \
  cli \
  peer chaincode instantiate \
    -o orderer.herec.uit:7050 \
    -C herecchannel \
    -n diagnosis \
    -l java \
    -v 1.0 \
    -c '{"Args":[]}' \
    -P "AND('ClientMSP.member','Quan12MSP.member')" \
    --tls \
    --cafile /opt/gopath/src/HerecUIT/hyperledger/fabric/peer/crypto/ordererOrganizations/herec.uit/orderers/orderer.herec.uit/msp/tlscacerts/tlsca.herec.uit-cert.pem \
    --peerAddresses peer0.client.herec.uit:7051 \
    --tlsRootCertFiles /opt/gopath/src/HerecUIT/hyperledger/fabric/peer/crypto/peerOrganizations/client.herec.uit/peers/peer0.client.herec.uit/tls/ca.crt

echo "Sleep 10s before submit transaction"
sleep 10

echo "Submitting initLedger transaction to smart contract on herecchannel"
docker exec \
  -e CORE_PEER_LOCALMSPID=ClientMSP \
  -e CORE_PEER_ADDRESS=peer0.client.herec.uit:7051 \
  -e CORE_PEER_MSPCONFIGPATH=${ORG1_MSPCONFIGPATH} \
  -e CORE_PEER_TLS_ROOTCERT_FILE=${ORG1_TLS_ROOTCERT_FILE} \
  cli \
  peer chaincode invoke \
    -o orderer.herec.uit:7050 \
    -C herecchannel \
    -n diagnosis \
    -c '{"function":"initLedger","Args":[]}' \
    --waitForEvent \
    --tls \
    --cafile /opt/gopath/src/HerecUIT/hyperledger/fabric/peer/crypto/ordererOrganizations/herec.uit/orderers/orderer.herec.uit/msp/tlscacerts/tlsca.herec.uit-cert.pem \
    --peerAddresses peer0.client.herec.uit:7051 \
    --peerAddresses peer0.quan12.herec.uit:9051 \
    --tlsRootCertFiles /opt/gopath/src/HerecUIT/hyperledger/fabric/peer/crypto/peerOrganizations/client.herec.uit/peers/peer0.client.herec.uit/tls/ca.crt \
    --tlsRootCertFiles /opt/gopath/src/HerecUIT/hyperledger/fabric/peer/crypto/peerOrganizations/quan12.herec.uit/peers/peer0.quan12.herec.uit/tls/ca.crt 
set +x

cat <<EOF
EOF
