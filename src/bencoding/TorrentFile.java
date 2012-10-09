import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class TorrentFile {

        public TorrentFile(String file) {
                this(new Bencoding(file));
        }
        public TorrentFile(Bencoding ben) {
                this.ben = ben;
        }
        
        public URL getAnnounce() {
                URL url = new URL(ben.get("announce"));
                return url;
                
        }
        public void init() {
                ben.parse();
        }
                
        //optional
        public List getAnnounceList() {}
        
        public int getPieceLength() {
                HashMap infohash = getInfo();
                return infohash.get("piece length");
        }        
        public String getPieces() {
                HashMap infohash = getInfo();
                return infohash.get("pieces");
        }
        public List getPiecesList() {
                String str = getPieces();

                //return an ArrayList;
        }        
        public String getName() {
                
        }
        public int getFileLength() {
                HashMap infohash = getInfo();
                return infohash.get("length");
        }
        
        //optional
        public String getMD5Checksum() {}

        /**PRIVATE*/
        private HashMap getInfo() {
                HashMap infomap = ben.get("info");
        }

        private Bencoding ben;
}