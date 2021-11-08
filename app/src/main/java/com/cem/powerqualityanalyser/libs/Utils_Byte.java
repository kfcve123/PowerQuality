package com.cem.powerqualityanalyser.libs;

import android.content.Context;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Utils_Byte {
    private static final char[] hexArray = "0123456789ABCDEF".toCharArray();

    private Utils_Byte() {
    }

    public static byte[] hexStringToBytes(String string) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        for(int idx = 0; idx + 2 <= string.length(); idx += 2) {
            String hexStr = string.substring(idx, idx + 2);
            int intValue = Integer.parseInt(hexStr, 16);
            baos.write(intValue);
        }

        return baos.toByteArray();
    }

    public static String bytesToHexString(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];

        for(int j = 0; j < bytes.length; ++j) {
            int v = bytes[j] & 255;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 15];
        }

        return new String(hexChars);
    }

    public static String bytesToHexString(byte bytes) {
        return bytesToHexString(new byte[]{bytes});
    }

    /** @deprecated */
    @Deprecated
    /*public static List<byte[]> fileToBinaryDataList(Context context, String file, int offset) {
        List<byte[]> binaryData = new ArrayList();
        BufferedReader reader = null;

        label105: {
            String currentLine;
            try {
                InputStream stream = context.getAssets().open(file);
                reader = new BufferedReader(new InputStreamReader(stream));

                while(true) {
                    if ((currentLine = reader.readLine()) == null) {
                        break label105;
                    }

                    String rawLine = currentLine.substring(1);
                    byte[] data = hexStringToBytes(rawLine);
                    long data_1 = (long)(0 | data[1]);
                    data_1 <<= 8;
                    data_1 &= 65280L;
                    long data_2 = (long)(0 | data[2]);
                    data_2 &= 255L;
                    long addr = data_1 + data_2;
                    long type = (long)data[3];
                    type &= 255L;
                    if (type != 0L || addr >= (long)offset) {
                        addr -= (long)offset;
                        data[1] = (byte)((int)((addr & 65280L) >>> 8));
                        data[2] = (byte)((int)(addr & 255L));
                        byte[] subBytes = subBytes(data, 0, data.length - 1);
                        binaryData.add(subBytes);
                    }
                }
            } catch (IOException var26) {
                currentLine = null;
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException var25) {
                        var25.printStackTrace();
                    }
                }

            }

            return currentLine;
        }

        if (binaryData != null) {
            Collections.reverse(binaryData);
        }

        return binaryData;
    }*/

    public static byte[] subBytes(byte[] source, int sourceBegin_index_inclusive, int sourceEnd_index_exclusive) {
        byte[] destination = new byte[sourceEnd_index_exclusive - sourceBegin_index_inclusive];
        System.arraycopy(source, sourceBegin_index_inclusive, destination, 0, sourceEnd_index_exclusive - sourceBegin_index_inclusive);
        return destination;
    }

    public static byte[] subBytes(byte[] source, int sourceBegin) {
        return subBytes(source, sourceBegin, source.length - 1);
    }

    public static void memcpy(byte[] dest, byte[] source, int size, int destOffset, int sourceOffset) {
        for(int i = 0; i < size; ++i) {
            dest[i + destOffset] = source[i + sourceOffset];
        }

    }

    public static void memcpy(byte[] dest, byte[] source, int size) {
        memcpy(dest, source, size, 0, 0);
    }

    public static void memset(byte[] data, byte value, int size) {
        for(int i = 0; i < size; ++i) {
            data[i] = value;
        }

    }

    public static void memset(byte[] data, byte value, int offset, int size) {
        if (offset >= 0 && data.length - offset >= size) {
            for(int i = offset; i < size; ++i) {
                data[i] = value;
            }

        }
    }

    public static boolean memcmp(byte[] buffer1, byte[] buffer2, int size) {
        for(int i = 0; i < size; ++i) {
            if (buffer1[i] != buffer2[i]) {
                return false;
            }
        }

        return true;
    }

    /** @deprecated */
    @Deprecated
    public static int getIntValue(byte[] data) {
        byte[] data_padded = new byte[4];
        memcpy(data_padded, data, data.length);
        int value = ByteBuffer.wrap(data_padded).getInt();
        value = Integer.reverseBytes(value);
        return value;
    }

    public static void reverseBytes(byte[] data) {
        for(int i = 0; i < data.length / 2; ++i) {
            byte first = data[i];
            byte last = data[data.length - 1 - i];
            data[i] = last;
            data[data.length - 1 - i] = first;
        }

    }

    public static short unsignedByte(byte value) {
        return (short)(value & 255);
    }

    public static byte[] shortToBytes(short l) {
        byte[] result = new byte[2];

        for(short i = 1; i >= 0; --i) {
            result[i] = (byte)(l & 255);
            l = (short)(l >> 8);
        }

        return result;
    }

    public static int bytesToShort(byte[] b) {
        int result = 0;

        for(short i = 0; i < 2; ++i) {
            result <<= 8;
            result |= b[i] & 255;
        }

        return result;
    }

    public static int bytes3ToShort(byte[] b) {
        int result = 0;

        for(short i = 0; i < 3; ++i) {
            result <<= 8;
            result |= b[i] & 255;
        }

        return result;
    }

    public static byte boolToByte(boolean value) {
        return (byte)(value ? 1 : 0);
    }

    public static boolean byteToBool(byte val) {
        return val == 1;
    }

    public static byte[] intToBytes(int l) {
        byte[] result = new byte[4];

        for(int i = 3; i >= 0; --i) {
            result[i] = (byte)(l & 255);
            l >>= 8;
        }

        return result;
    }

    public static int bytesToInt(byte[] b) {
        int result = 0;

        for(int i = 0; i < 4; ++i) {
            result <<= 8;
            if (i < b.length) {
                result |= b[i] & 255;
            }
        }

        return result;
    }

    public static byte[] longToBytes(long l) {
        byte[] result = new byte[8];

        for(int i = 7; i >= 0; --i) {
            result[i] = (byte)((int)(l & 255L));
            l >>= 8;
        }

        return result;
    }

    public static long bytesToLong(byte[] b) {
        long result = 0L;

        for(int i = 0; i < 8; ++i) {
            result <<= 8;
            result |= (long)(b[i] & 255);
        }

        return result;
    }

    public static byte[] floatToBytes(float f) {
        int intBits = Float.floatToIntBits(f);
        return intToBytes(intBits);
    }

    public static float bytesToFloat(byte[] b) {
        int intBits = bytesToInt(b);
        return Float.intBitsToFloat(intBits);
    }

    public static byte[] doubleToBytes(double d) {
        long longBits = Double.doubleToLongBits(d);
        return longToBytes(longBits);
    }

    public static double bytesToDouble(byte[] b) {
        long longBits = bytesToLong(b);
        return Double.longBitsToDouble(longBits);
    }

}
