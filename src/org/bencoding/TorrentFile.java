package bencoding;

import java.net.URL;

import java.io.IOException;
import java.io.FileNotFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class TorrentFile {

        public TorrentFile(String file) throws FileNotFoundException {
                this(new RecursiveBencoding(file));
        }
        public TorrentFile(Bencoding ben) {
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

        private Bencoding ben;
        private HashMap root;
}