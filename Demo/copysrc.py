#!/usr/bin/python

import shutil
import sys

shutil.copy2(sys.argv[1], '/DCNFS/users/student/smaram/KeyValueStore/PeerNode1/main/')
 
shutil.copy2(sys.argv[1], '/DCNFS/users/student/smaram/KeyValueStore/PeerNode2/main/')
shutil.copy2(sys.argv[1], '/DCNFS/users/student/smaram/KeyValueStore/PeerNode3/main/')
shutil.copy2(sys.argv[1], '/DCNFS/users/student/smaram/KeyValueStore/PeerNode4/main/')
shutil.copy2(sys.argv[1], '/DCNFS/users/student/smaram/KeyValueStore/PeerNode5/main/')
