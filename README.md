android_webview_PasswordFinder
==============================

Simple tool to show all plaintext passwords stored on a device

java version of:
su
find /data/data/. | grep webview.db | while read line
 do sqlite3 -cmd "SELECT * FROM password;" ${line} '.quit'
done
