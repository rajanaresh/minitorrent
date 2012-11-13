package org;

import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.net.URLEncoder;
import java.net.InetAddress;
import java.util.*;
import java.io.*;
import org.apache.commons.codec.net.URLCodec;

import org.bencoding.*;

public class Test {
        public static void main(String[] args) throws Exception {

                TorrentFile file = new TorrentFile("./src/org/bencoding/file3.torrent");
                                                
                String u = file.getAnnounce().toString();
                u = u.concat("?info_hash=");

                byte[] infohash = file.getInfoHashSHA1();
                
                URLCodec codec = new URLCodec();
                infohash = codec.encode(infohash);

                String urlencodedhash = new String(infohash);
                u = u.concat(urlencodedhash);

                u = u.concat("&peer_id=12345678901234567890&uploaded=0&downloaded=0&left=1000&event=started");
                URL url = new URL(u);
                System.out.println(" ");
                System.out.println(url);
                
                InputStream in = url.openStream();
                int c = 0;
                char ch = (char)c;
                
                while((c=in.read()) != -1) {
                        ch = (char)c;
                        System.out.print(ch);
                }
        
        }
                
}

