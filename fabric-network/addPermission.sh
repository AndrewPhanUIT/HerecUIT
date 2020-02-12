CONFIG_ROOT=/opt/gopath/src/HerecUIT/hyperledger/fabric/peer
CC_SRC_PATH=/opt/gopath/src/HerecUIT/chaincode/java
CC_RUNTIME_LANGUAGE=java

MSP=$1
PEER_PORT=$2
ORG_NAME_LOWER=$3
CHANNEL=$4
CHAINCODE=$5

MSP_CONFIGPATH=${CONFIG_ROOT}/crypto/peerOrganizations/${ORG_NAME_LOWER}.herec.uit/users/Admin@${ORG_NAME_LOWER}.herec.uit/msp
TLS_ROOTCERT_FILE=${CONFIG_ROOT}/crypto/peerOrganizations/${ORG_NAME_LOWER}.herec.uit/peers/${PEER_NAME}.${ORG_NAME_LOWER}.herec.uit/tls/ca.crt

set -x
echo "Start adding new org to channel ${CHANNEL}"
docker exec \
  -e CORE_PEER_LOCALMSPID=$MSP \
  -e CORE_PEER_ADDRESS=peer0.client.herec.uit:${PEER_PORT} \
  -e CORE_PEER_MSPCONFIGPATH=$MSP_CONFIGPATH \
  -e CORE_PEER_TLS_ROOTCERT_FILE=${TLS_ROOTCERT_FILE} \
  cli \
  peer channel join -b $CHANNEL.block >&log.txt

echo "Installing smart contract on peer.${ORG_NAME_LOWER}.herec.uit"
docker exec \
  -e CORE_PEER_LOCALMSPID=$MSP \
  -e CORE_PEER_ADDRESS=peer0.client.herec.uit:${PEER_PORT} \
  -e CORE_PEER_MSPCONFIGPATH=$MSP_CONFIGPATH \
  -e CORE_PEER_TLS_ROOTCERT_FILE=${TLS_ROOTCERT_FILE} \
  cli \
  peer chaincode install \
    -n ${CHAINCODE} \
    -v 1.0 \
    -p "$CC_SRC_PATH" \
    -l "$CC_RUNTIME_LANGUAGE"