package bencoding;

import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileNotFoundException;

import java.util.HashMap;
import java.util.ArrayList;
import java.lang.Integer;

class RecursiveBencoding implements Bencoding {

        //Use only BufferedInputStream
        RecursiveBencoding(String file) throws FileNotFoundException {
                this(file, new BufferedInputStream(new FileInputStream(file)));
        }
        
        RecursiveBencoding(String file, InputStream r) {
                this.file = file;
                this.r = r;
        }

        public Object parse() throws IOException {
                Object x = null;
                char c = (char)r.read();

                if(c == 'l') {
                        x = new ArrayList<Object>();
                        r.mark(3);
                        while((c=(char)r.read()) != 'e') {
                                r.reset();
                                ((ArrayList)x).add(parse());
                                r.mark(3);
                        }
                        return x;
                }
                else if(c == 'd') {
                        x = new HashMap<String, Object>();
                        r.mark(3);
                        String key = null;
                        Object value = null;
                        while((c=(char)r.read()) != 'e') {
                                r.reset();
                                key = (String)parse();
                                value = parse();
                                ((HashMap)x).put(key, value);
                                r.mark(3);
                        }
                        return x;
                }
                else if(c == 'i') {
                        int num = 0;
                        int ch = 0;
                        while(((ch=r.read()) != 101) && (ch >= 48) && (ch <= 57)) {
                                num = (num * 10) + (ch - 48);
                        }
                        x = new Integer(num);
                        return x;
                }
                else {
                        int num = (int)c;
                        num = num - 48;
                        int ch = (int)c;
                        while(((ch=r.read()) != 58) && (ch >= 48) && (ch <= 57)) {
                                num = (num * 10) + (ch - 48);
                        }
                        if(num > 0) {
                                byte[] str = new byte[num];
                                r.read(str, 0, num);
                                x = new String(str);
                        }
                        return x;
                }
        }

        private InputStream r;
        private String file;
}