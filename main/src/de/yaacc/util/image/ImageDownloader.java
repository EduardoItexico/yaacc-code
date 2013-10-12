package de.yaacc.util.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

/**
 * Manages downloading of images from various sources and some refactoring to these images.
 *
 * @author Christoph Hähnel (eyeless)
 */
public class ImageDownloader {

    public ImageDownloader(){}

    /**
     * Loads an iconised version of the handed image location
     * @param imageUri image location
     * @return
     */
    public Bitmap retrieveIcon(Uri imageUri){
        Bitmap result = null;
        try{
            result = decodeSampledBitmapFromStream(imageUri, 48, 48);
        } catch (IOException e){
            Log.d(this.getClass().getName(),"while decoding image: "+e.getMessage());
        }
        return result;
    }

    /**
     * Loads an image from the given URI and return a Bitmap that matchs the requested size
     * @param imageUri image location
     * @param reqWidth width of result image
     * @param reqHeight height of result image
     * @return requested image
     * @throws IOException problem while loading image from stream
     */
    private Bitmap decodeSampledBitmapFromStream(Uri imageUri, int reqWidth,
                                                 int reqHeight) throws IOException {
        InputStream is = getUriAsStream(imageUri);

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inPreferQualityOverSpeed = false;
        options.inDensity = DisplayMetrics.DENSITY_LOW;
        options.inTempStorage = new byte[7680016];
        Log.d(this.getClass().getName(),
                "displaying image size width, height, inSampleSize "
                        + options.outWidth + "," + options.outHeight + ","
                        + options.inSampleSize);
        Log.d(this.getClass().getName(), "free memory before image load: "
                + Runtime.getRuntime().freeMemory());
        Bitmap bitmap = BitmapFactory.decodeStream(new FlushedInputStream(is),
                null, options);

        bitmap = Bitmap.createScaledBitmap(bitmap,reqWidth,reqHeight,false);
        Log.d(this.getClass().getName(), "free memory after image load: "
                + Runtime.getRuntime().freeMemory());

        if(bitmap.getHeight() != reqHeight){
            Log.w(this.getClass().getName(), "Bitmap has wrong size !!! height: "+bitmap.getHeight()+" width: "+bitmap.getWidth());
        }
        return bitmap;
    }

    /**
     * Converts URI to InputStream.
     * @param imageUri
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     * @throws MalformedURLException
     */
    private InputStream getUriAsStream(Uri imageUri)
            throws FileNotFoundException, IOException, MalformedURLException {
        InputStream is = null;
        Log.d(getClass().getName(), "Start load: " + System.currentTimeMillis());

        is = (InputStream) new java.net.URL(imageUri.toString())
                .getContent();
        Log.d(getClass().getName(), "Stop load: " + System.currentTimeMillis());
        Log.d(getClass().getName(), "InputStream: " + is);
        return is;
    }

    static class FlushedInputStream extends FilterInputStream {
        public FlushedInputStream(InputStream inputStream) {
            super(inputStream);
        }

        @Override
        public long skip(long n) throws IOException {
            long totalBytesSkipped = 0L;
            while (totalBytesSkipped < n) {
                long bytesSkipped = in.skip(n - totalBytesSkipped);
                if (bytesSkipped == 0L) {
                    int byte_ = read();
                    if (byte_ < 0) {
                        break; // we reached EOF
                    } else {
                        bytesSkipped = 1; // we read one byte
                    }
                }
                totalBytesSkipped += bytesSkipped;
            }
            return totalBytesSkipped;
        }
    }


}
