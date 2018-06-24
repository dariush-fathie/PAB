package ir.paad.audiobook.customClass;


/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import android.net.Uri;
import android.util.Log;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.TransferListener;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import ir.paad.audiobook.utils.CipherUtil;

/**
 * A {@link DataSource} for reading local files.
 */
public final class CustomFileDataSource implements DataSource {

    /**
     * Thrown when IOException is encountered during local file read operation.
     */
    public static class FileDataSourceException extends IOException {
        public FileDataSourceException(IOException cause) {
            super(cause);
        }
    }

    private final TransferListener<? super CustomFileDataSource> listener;

    //private RandomAccessFile file;
    private Uri uri;
    private long bytesRemaining;
    private boolean opened;

    public CustomFileDataSource() {
        this(null);
    }

    /**
     * @param listener An optional listener.
     */
    public CustomFileDataSource(TransferListener<? super CustomFileDataSource> listener) {
        this.listener = listener;
    }

    CipherInputStream cipherInputStream ;
    File file1 ;
    @Override
    public long open(DataSpec dataSpec) throws FileDataSourceException {

        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("AES/CTR/NoPadding");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
        SecretKeySpec keySpec = new SecretKeySpec("0123456789123456".getBytes(), "AES");
        IvParameterSpec ivSpec = new IvParameterSpec("0123456789654321".getBytes());
        try {
            assert cipher != null;
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }


        Log.e("open ", "start");
        try {
            uri = dataSpec.uri;
            //file = new RandomAccessFile(dataSpec.uri.getPath(), "r");

            file1 = new File(dataSpec.uri.getPath());
            cipherInputStream = new CipherInputStream(new FileInputStream(file1), cipher){
                @Override
                public int available() throws IOException {
                    return in.available();
                }
            };

            long skipped = cipherInputStream.skip(dataSpec.position);

            if (skipped < dataSpec.position){
                throw new EOFException();
            }

            //file.seek(dataSpec.position);

            bytesRemaining = dataSpec.length == C.LENGTH_UNSET ? file1.length() - dataSpec.position
                    : dataSpec.length;
            if (bytesRemaining < 0) {
                throw new EOFException();
            }
        } catch (IOException e) {
            throw new FileDataSourceException(e);
        }

        opened = true;
        if (listener != null) {
            listener.onTransferStart(CustomFileDataSource.this, dataSpec);
        }

        return bytesRemaining;
    }

    @Override
    public int read(byte[] buffer, int offset, int readLength) throws FileDataSourceException {
        //Log.e("read ", "start");
        if (readLength == 0) {
            return 0;
        } else if (bytesRemaining == 0) {
            return C.RESULT_END_OF_INPUT;
        } else {
            int bytesRead;
            try {
                //bytesRead = file.read(buffer, offset, (int) Math.min(bytesRemaining, readLength));
                bytesRead = cipherInputStream.read(buffer, offset, (int) Math.min(bytesRemaining, readLength));
            } catch (IOException e) {
                throw new FileDataSourceException(e);
            }

            if (bytesRead > 0) {
                bytesRemaining -= bytesRead;
                if (listener != null) {
                    listener.onBytesTransferred(this, bytesRead);
                }
            }

            return bytesRead;
        }
    }

    @Override
    public Uri getUri() {
        return uri;
    }

    @Override
    public void close() throws FileDataSourceException {
        uri = null;
        /*try {
            if (file != null) {
                file.close();
            }
        } catch (IOException e) {
            throw new FileDataSourceException(e);
        } finally {
            file = null;
            if (opened) {
                opened = false;
                if (listener != null) {
                    listener.onTransferEnd(this);
                }
            }
        }*/
        try {
            cipherInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        file1 = null;
        if (opened) {
            opened = false;
            if (listener != null) {
                listener.onTransferEnd(this);
            }
        }

    }

}

