package org.bencoding;

import java.net.URL;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.BufferedInputStream;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class TorrentFile {

        public TorrentFile(String file) throws FileNotFoundException {
                this(file, new RecursiveBencoding(file));
        }
        public TorrentFile(String file, Bencoding ben) {
                this.file = file;
                this.ben = ben;
        }
        public void init() {
                try {
                        root = (HashMap)ben.parse();
                } catch(IOException e) {
                        e.printStackTrace();
                }
        }
        public URL getAnnounce() throws Exception {
                URL url = new URL((String)root.get("announce"));
                return url;
        }
                        
        //optional
        //public List getAnnounceList() {}
        
        public int getPieceLength() {
                HashMap infohash = getInfo();
                Integer i = (Integer)infohash.get("piece length");
                return i.intValue();
        }        
        public String getPieces() {
                HashMap infohash = getInfo();
                return (String)infohash.get("pieces");
        }
        /**
           public List getPiecesList() {
                String str = getPieces();

                //return an ArrayList;
        }
        
        public String getName() {
                
        }
        */
        public int getFileLength() {
                HashMap infohash = getInfo();
                Integer i = (Integer)infohash.get("length");
                return i.intValue();
        }
        
        //optional
        //public String getMD5Checksum() {}

        /**PRIVATE*/
        private HashMap getInfo() {
                HashMap infomap = (HashMap)root.get("info");
                return infomap;
        }

        //throws three exceptions
        public byte[] getInfoHashSHA1() throws Exception {
                
                InputStream in = new BufferedInputStream(new FileInputStream(file));
                MessageDigest mesg = MessageDigest.getInstance("SHA-1");
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
                
                //removing last e in the bencoded dictionary
                list.remove(list.size()-1);

                //List to Array
                Byte[] info = new Byte[list.size()];
                info = list.toArray(info);

                //unboxing Byte to byte Array
                byte[] infodict = new byte[info.length];
                for(int i=0; i<info.length; i++) {
                        infodict[i] = info[i];
                }

                //getting the 20 byte SHA-1 hash
                byte[] sha = mesg.digest(infodict);

                return sha;
        }

        private Bencoding ben;
        private HashMap root;
        private String file;
}