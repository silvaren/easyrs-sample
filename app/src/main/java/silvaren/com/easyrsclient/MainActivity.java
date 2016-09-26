package silvaren.com.easyrsclient;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v8.renderscript.Matrix3f;
import android.support.v8.renderscript.RenderScript;
import android.util.Log;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

import io.github.silvaren.easyrs.tools.Blend;
import io.github.silvaren.easyrs.tools.Blur;
import io.github.silvaren.easyrs.tools.ColorMatrix;
import io.github.silvaren.easyrs.tools.Convolve;
import io.github.silvaren.easyrs.tools.Histogram;
import io.github.silvaren.easyrs.tools.Lut;
import io.github.silvaren.easyrs.tools.Lut3D;
import io.github.silvaren.easyrs.tools.Nv21Image;
import io.github.silvaren.easyrs.tools.Resize;
import io.github.silvaren.easyrs.tools.YuvToRgb;
import io.github.silvaren.easyrs.tools.params.ConvolveParams;
import io.github.silvaren.easyrs.tools.params.SampleParams;

public class MainActivity extends AppCompatActivity {

    public static final int ITERATIONS = 50;
    public static final int TARGET_WIDTH = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume(){
        super.onResume();

        RenderScript rs = RenderScript.create(this);
        Nv21Image nv21ImageSample = Nv21Image.generateSample();
        byte[] resized = Resize.resize(rs, nv21ImageSample.nv21ByteArray, nv21ImageSample.width, nv21ImageSample.height, 51, 51);
        byte[] nv21ImageBytes = Resize.resize(rs, nv21ImageSample.nv21ByteArray, nv21ImageSample.width, nv21ImageSample.height,
                TARGET_WIDTH, TARGET_WIDTH);
        Nv21Image nv21Image = new Nv21Image(nv21ImageBytes, TARGET_WIDTH, TARGET_WIDTH);
        Bitmap inputBitmap = Nv21Image.nv21ToBitmap(rs, nv21ImageSample);
//        Bitmap inputBitmap2 = Nv21Image.nv21ToBitmap(rs, Nv21Image.generateSample());
//        Blend.add(rs, inputBitmap, inputBitmap2);
//        Matrix3f matrix3f = new Matrix3f();
//        Bitmap outputBitmap = ColorMatrix.applyMatrix(rs, inputBitmap, matrix3f);
//        Bitmap outputBitmap = Convolve.convolve3x3(rs, inputBitmap, coefficients);
//        int[] histograms = Histogram.rgbaHistograms(rs, inputBitmap);
//        Bitmap outputBitmap = Lut.applyLut(rs, inputBitmap, rgbaLut);
//        Bitmap outputBitmap = Lut3D.apply3dLut(rs, inputBitmap, cube);
//        Bitmap outputBitmap = Resize.resize(rs, inputBitmap, targetWidth, targetHeight);
//        YuvToRgb.yuvToRgb(rs, nv)
//        Nv21Image nv21Image = Nv21Image.bitmapToNV21(rs, inputBitmap);

//        for (int i = 0; i < ITERATIONS; i++) {
//            long start = System.currentTimeMillis();
//            YuvImage yuvImage = new YuvImage(nv21Image.nv21ByteArray, ImageFormat.NV21, nv21Image.width, nv21Image.height, null);
//            ByteArrayOutputStream os = new ByteArrayOutputStream();
//            yuvImage.compressToJpeg(new Rect(0, 0, nv21Image.width, nv21Image.height), 100, os);
//            byte[] jpegByteArray = os.toByteArray();
//            Bitmap bitmap = BitmapFactory.decodeByteArray(jpegByteArray, 0, jpegByteArray.length);
//            Log.d("benchmark", "JPEG process: " + (System.currentTimeMillis() - start));
//        }
//        for (int i = 0; i < ITERATIONS; i++) {
//            long start = System.currentTimeMillis();
//            Nv21Image.nv21ToBitmap(rs, nv21Image.nv21ByteArray, nv21Image.width, nv21Image.height);
//            Log.d("benchmark", "RS process: " + (System.currentTimeMillis() - start));
//        }

//        Bitmap blurredBitmap = Blur.blur(rs, inputBitmap, 25.f);
//        Bitmap blurredBitmap = Lut.applyLut(rs, inputBitmap, SampleParams.Lut.negative());

        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageBitmap(Nv21Image.nv21ToBitmap(rs, resized, 51, 51));
//        imageView.setImageBitmap(Resize.resize(rs, inputBitmap, 51, 51));
    }
}
