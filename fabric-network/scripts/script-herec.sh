#!/bin/bash

echo
echo " ____    _____      _      ____    _____ "
echo "/ ___|  |_   _|    / \    |  _ \  |_   _|"
echo "\___ \    | |     / _ \   | |_) |   | |  "
echo " ___) |   | |    / ___ \  |  _ <    | |  "
echo "|____/    |_|   /_/   \_\ |_| \_\   |_|  "
echo
echo "Building your herec network"
echo
CHANNEL_NAME="herecchannel"
DELAY="3"
LANGUAGE="java"
TIMEOUT="10"
VERBOSE="false"
NO_CHAINCODE="false"
COUNTER=1
MAX_RETRY=10

CC_SRC_PATH=/opt/gopath/src/HerecUIT/chaincode/java

# import utils
. scripts/utils-herec.sh

createChannel() {
	setGlobals 0 1
	set -x
	peer channel create -o orderer.herec.uit:7050 -c $CHANNEL_NAME -f ./channel-artifacts/herecchannel.tx --tls --cafile $ORDERER_CA >&log.txt
	res=$?
	set +x
	cat log.txt
	verifyResult $res "Channel creation failed"
	echo "===================== Channel '$CHANNEL_NAME' created ===================== "
	echo
}

joinChannel () {
	for org in 1 2; do
	    for peer in 0 1; do
		joinChannelWithRetry $peer $org
		echo "===================== peer${peer}.org${org} joined channel '$CHANNEL_NAME' ===================== "
		sleep $DELAY
		echo
	    done
	done
}

sleep 15

## Create channel
echo "Creating channel..."
createChannel

# ## Join all the peers to the channel
# echo "Having all peers join the channel..."
joinChannel

# sleep 15

# ## Set the anchor peers for each org in the channel
echo "Updating anchor peers for client..."
updateAnchorPeers 0 1
# echo "Updating anchor peers for quan12..."
updateAnchorPeers 0 2

# echo "Installing chaincode on peer0.client..."
# installChaincode 0 1
# echo "Install chaincode on peer0.quan12..."
# installChaincode 0 2

# echo "Instantiating chaincode on peer0.client..."
# instantiateChaincode 0 1

# echo "Sending invoke transaction on peer0.client peer0.quan12..."
# chaincodeInvoke 0 1 0 2

echo
echo "========= All GOOD, HEREC_NETWORK execution completed =========== "
echo

echo
echo " _____   _   _   ____   "
echo "| ____| | \ | | |  _ \  "
echo "|  _|   |  \| | | | | | "
echo "| |___  | |\  | | |_| | "
echo "|_____| |_| \_| |____/  "
echo

exit 0
