#!/usr/bin/python
import os

#os.chdir("main")
os.system("javac /DCNFS/users/student/smaram/KeyValueStore/NodeCollection/main/*.java") 
os.system("javac /DCNFS/users/student/smaram/KeyValueStore/PeerNode1/main/*.java") 

os.system("javac /DCNFS/users/student/smaram/KeyValueStore/PeerNode2/main/*.java") 
os.system("javac /DCNFS/users/student/smaram/KeyValueStore/PeerNode3/main/*.java") 
os.system("javac /DCNFS/users/student/smaram/KeyValueStore/PeerNode4/main/*.java") 
os.system("javac /DCNFS/users/student/smaram/KeyValueStore/PeerNode5/main/*.java") 
print "Compiled successfully!"
"""os.system("cd /home/smaram/KeyValueStore/PeerNode2/main")
os.system("javac *.java")
os.system("cd /home/smaram/KeyValueStore/PeerNode3/main")
os.system("javac *.java")
os.system("cd /home/smaram/KeyValueStore/PeerNode4/main")
os.system("javac *.java")
os.system("cd /home/smaram/KeyValueStore/PeerNode5/main")
os.system("javac *.java")"""

