#!/usr/bin/expect -f
set password [lrange $argv 0 0] 
spawn sudo wondershaper eth0 clear
expect "*for*"
# Send password aka $password
send -- "$password\r"
# send blank line (\r) to make sure we get back to gui
send -- "\r"
expect eof
spawn sudo wondershaper wlan0 clear
expect "*for*"
# Send password aka $password
send -- "$password\r"
# send blank line (\r) to make sure we get back to gui
send -- "\r"
expect eof
