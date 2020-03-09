CONFIG_ROOT=/opt/gopath/src/HerecUIT/hyperledger/fabric/peer

MSP=$1
PEER_NAME=$2
PEER_PORT=$3
ORG_NAME_LOWER=$4
CHANNEL=$5
CHAINCODE=$6
CHAINCODE_SCRIPT=$7

MSP_CONFIGPATH=${CONFIG_ROOT}/crypto/peerOrganizations/${ORG_NAME_LOWER}.herec.uit/users/Admin@${ORG_NAME_LOWER}.herec.uit/msp
TLS_ROOTCERT_FILE=${CONFIG_ROOT}/crypto/peerOrganizations/${ORG_NAME_LOWER}.herec.uit/peers/${PEER_NAME}.${ORG_NAME_LOWER}.herec.uit/tls/ca.crt

set -x
docker exec \
  -e CORE_PEER_LOCALMSPID=$MSP \
  -e CORE_PEER_ADDRESS=${PEER_NAME}.${ORG_NAME_LOWER}.herec.uit:${PEER_PORT} \
  -e CORE_PEER_MSPCONFIGPATH=$MSP_CONFIGPATH \
  -e CORE_PEER_TLS_ROOTCERT_FILE=${TLS_ROOTCERT_FILE} \
  cli \
  peer chaincode query \
    -o orderer.herec.uit:7050 \
    -C $CHANNEL \
    -n $CHAINCODE \
    -c $CHAINCODE_SCRIPT