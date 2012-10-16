import java.net.URL;
import java.security.MessageDigest;
import java.net.URLEncoder;
import java.net.InetAddress;
import java.util.*;
import java.io.*;

public class Utils {
        public static void main(String[] args) throws Exception {
                MessageDigest mesg = MessageDigest.getInstance("SHA-1");
                
                InetAddress inet = InetAddress.getByName("localhost");
                byte[] address = inet.getAddress();

                System.out.println(inet.getHostAddress());
                                                
                byte[] sha = mesg.digest(address);
                String sha1 = new String(sha);
                System.out.println(sha1);

                String urlsha1 = URLEncoder.encode(sha1, "UTF-8");
                System.out.println(urlsha1);

        }

        public static byte[] getInfoHashSHA1(String torrentfile) {
                
                InputStream in = new BufferedInputStream(new FileInputStream(torrentfile));
                int c = in.read();
                char ch = (char)c;
                byte[] check = new byte[4];
                byte b = 0;

                List<Byte> list = new ArrayList<Byte>();
                
                while(c != -1) {
                        if(ch == '4') {
                                in.mark(10);
                                ch = (char)in.read();
                                if(ch == ':') {
                                        in.read(check, 0, check.length);
                                        String str = new String(check);
                                        if(str.equals("info")) {
                                                c = in.read();
                                                while(c != -1) {
                                                        b = (byte)c;
                                                        list.add(b);
                                                        c = in.read();
                                                }
                                        }
                                        else {
                                                in.reset();
                                        }
                                }
                                else {
                                        in.reset();     
                                }
                        }
                        c = in.read();
                        ch = (char)c;
                }
                int x = list.size();
                System.out.println(x);
                
                list.remove(list.size()-1);
                Byte[] info = new Byte[list.size()];
                info = list.toArray(info);

                byte[] infodict = new byte[info.length];
                for(int i=0; i<info.length; i++) {
                        infodict[i] = info[i];
                }
                
                byte[] sha = mesg.digest(infodict);
                                
        }
}