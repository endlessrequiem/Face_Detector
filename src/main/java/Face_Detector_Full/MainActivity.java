package Face_Detector_Full;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.face.FirebaseVisionFace;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceLandmark;

import java.io.File;
import java.util.List;

import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();

    ImageView imageView;
    TextView details;
    Button gallery, camera;

    File jpgimage = null;
    String path = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        details = findViewById(R.id.details);

        imageView = findViewById(R.id.imageView);

        gallery = findViewById(R.id.gallery);
        camera = findViewById(R.id.camera);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Opens camera dialog
                EasyImage.openCamera(MainActivity.this, 100);
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Opens gallery picker
                EasyImage.openGallery(MainActivity.this, 100);
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                Toast.makeText(MainActivity.this, "Image picker error", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onImagePickerError: " + "Image picker error");
            }

            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {


                FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(new BitmapFactory().decodeFile(imageFile.getAbsolutePath()));
                Toast.makeText(MainActivity.this, "Image access success!!!", Toast.LENGTH_LONG).show();


                FirebaseVisionFaceDetectorOptions options =
                        new FirebaseVisionFaceDetectorOptions.Builder()
                                .setModeType(FirebaseVisionFaceDetectorOptions.ACCURATE_MODE)
                                .setLandmarkType(FirebaseVisionFaceDetectorOptions.ALL_LANDMARKS)
                                .setClassificationType(FirebaseVisionFaceDetectorOptions.ALL_CLASSIFICATIONS)
                                .setMinFaceSize(0.15f)
                                .setTrackingEnabled(true)
                                .build();


                FirebaseVisionFaceDetector detector = FirebaseVision.getInstance()
                        .getVisionFaceDetector(options);


                Task<List<FirebaseVisionFace>> result =
                        detector.detectInImage(image)
                                .addOnSuccessListener(
                                        new OnSuccessListener<List<FirebaseVisionFace>>() {
                                            @Override
                                            public void onSuccess(List<FirebaseVisionFace> faces) {

                                                for (FirebaseVisionFace face : faces) {

                                                    //Firebase Vision Landmark will find each facial part, and then send back the position
                                                    // of each facial part in the photo
                                                    FirebaseVisionFaceLandmark leftEar = face.getLandmark(FirebaseVisionFaceLandmark.LEFT_EAR);
                                                    String leN;
                                                    leN = String.valueOf(leftEar);
                                                    //N represents Null, you want to use the bottom string (le in this case)
                                                    //otherwise output will contain Firebase path info (as seen below)
                                                    String le = leN.replaceAll("com.google.firebase.ml.vision.face.FirebaseVisionFaceLandmark","");


                                                    FirebaseVisionFaceLandmark rightEar = face.getLandmark(FirebaseVisionFaceLandmark.RIGHT_EAR);
                                                    String reN;
                                                    reN = String.valueOf(rightEar);
                                                    String re = reN.replaceAll("com.google.firebase.ml.vision.face.FirebaseVisionFaceLandmark","");


                                                    FirebaseVisionFaceLandmark rightCheek = face.getLandmark(FirebaseVisionFaceLandmark.RIGHT_CHEEK);
                                                    String rcN;
                                                    rcN = String.valueOf(rightCheek);
                                                    String rc = rcN.replaceAll("com.google.firebase.ml.vision.face.FirebaseVisionFaceLandmark","");



                                                    FirebaseVisionFaceLandmark leftCheek = face.getLandmark(FirebaseVisionFaceLandmark.LEFT_CHEEK);
                                                    String lcN;
                                                    lcN = String.valueOf(leftCheek);
                                                    String lc = lcN.replaceAll("com.google.firebase.ml.vision.face.FirebaseVisionFaceLandmark","");



                                                    FirebaseVisionFaceLandmark bottomMouth = face.getLandmark(FirebaseVisionFaceLandmark.BOTTOM_MOUTH);
                                                    String bmN;
                                                    bmN = String.valueOf(bottomMouth);
                                                    String bm = bmN.replaceAll("com.google.firebase.ml.vision.face.FirebaseVisionFaceLandmark","");



                                                    FirebaseVisionFaceLandmark leftMouth = face.getLandmark(FirebaseVisionFaceLandmark.LEFT_MOUTH);
                                                    String lmN;
                                                    lmN = String.valueOf(leftMouth);
                                                    String lm = lmN.replaceAll("com.google.firebase.ml.vision.face.FirebaseVisionFaceLandmark","");



                                                    FirebaseVisionFaceLandmark rightMouth = face.getLandmark(FirebaseVisionFaceLandmark.RIGHT_MOUTH);
                                                    String rmN;
                                                    rmN = String.valueOf(rightMouth);
                                                    String rm = rmN.replaceAll("com.google.firebase.ml.vision.face.FirebaseVisionFaceLandmark","");



                                                    FirebaseVisionFaceLandmark noseBase = face.getLandmark(FirebaseVisionFaceLandmark.NOSE_BASE);
                                                    String nbN;
                                                    nbN = String.valueOf(noseBase);
                                                    String nb = nbN.replaceAll("com.google.firebase.ml.vision.face.FirebaseVisionFaceLandmark","");


                                                    //Each facial part position is given here, without \n the output would be one line
                                                    //instead of multiple lines
                                                    String facepositions;
                                                    facepositions = (le + " Left Ear Position \n"
                                                            + re + " Right Ear Position \n"
                                                            + rc + " Right Cheek Position \n"
                                                            + lc + " Left Cheek Position \n"
                                                            + bm + " Bottom Mouth Position \n"
                                                            + lm + " Left Mouth Position \n"
                                                            + rm + " Right Mouth Position \n"
                                                            + nb + " Nose Base Position \n");

                                                    //This finds a smile in the picture, and then gives the percentage found
                                                    if (face.getSmilingProbability() != FirebaseVisionFace.UNCOMPUTED_PROBABILITY) {
                                                        float smileProb = face.getSmilingProbability();
                                                        float finalProb = smileProb * 100;
                                                        String smile = "";
                                                        if (smileProb != 0) {
                                                            smile = String.valueOf(finalProb) + "%" + " of a Smile Detected \n";

                                                        }
                                                        //This finds the percentage of how open the right eye is
                                                        if (face.getRightEyeOpenProbability() != FirebaseVisionFace.UNCOMPUTED_PROBABILITY) {
                                                            float rightEyeOpenProb = face.getRightEyeOpenProbability();
                                                            float finalrighteyeopenProb = rightEyeOpenProb * 100;
                                                            String righteyeopen = "";
                                                            if (rightEyeOpenProb != 0) {
                                                                righteyeopen = String.valueOf(finalrighteyeopenProb) + "%" + " Right Eye Open \n";
                                                            }
                                                            //Gives the percentage of how open the left eye is
                                                            if (face.getLeftEyeOpenProbability() != FirebaseVisionFace.UNCOMPUTED_PROBABILITY) {
                                                                float LeftEyeOpenProb = face.getLeftEyeOpenProbability();
                                                                float finallefteyeopenProb = LeftEyeOpenProb * 100;
                                                                String lefteyeopen = "";
                                                                if (LeftEyeOpenProb != 0) {
                                                                    lefteyeopen = String.valueOf(finallefteyeopenProb) + "%" + " Left Eye Open \n";
                                                                }
                                                                //Gives the degrees of how far the head is tilted
                                                                if (face.getHeadEulerAngleZ() != FirebaseVisionFace.UNCOMPUTED_PROBABILITY) {
                                                                    float headanglez = face.getHeadEulerAngleZ();
                                                                    float headanglezProb = headanglez * 100;
                                                                    String headangz = "";
                                                                    if (headanglezProb != 0) {
                                                                        headangz = String.valueOf(headanglezProb) + " Degrees Head Tilted \n";
                                                                    }
                                                                    //Gives the degrees of how much the head is rotated
                                                                    if (face.getHeadEulerAngleY() != FirebaseVisionFace.UNCOMPUTED_PROBABILITY) {
                                                                        float headangley = face.getHeadEulerAngleY();
                                                                        float headangleyProb = headangley * 100;
                                                                        String headangy = "";
                                                                        if (headangley != 0) {
                                                                            headangy = String.valueOf(headangleyProb) + " Degrees Head Rotated \n";
                                                                        }

                                                                        String end;
                                                                        //Gives a couple lines between the end of results and the rest of the output
                                                                        end = "\n\nEnd Results.";
                                                                        details.setText(smile + righteyeopen + lefteyeopen
                                                                                + headangz + headangy + facepositions + end);
                                                                    }
                                                                }
                                                            }

                                                        }
                                                    }
                                                }
                                            }
                                        })
                                .addOnFailureListener(
                                        new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                //This is what happens when there is an error
                                                Toast.makeText(MainActivity.this, "Error, please try again", Toast.LENGTH_LONG).show();

                                            }
                                        });

                FirebaseVisionFace faces;


                jpgimage = imageFile;
                path = imageFile.getAbsolutePath();
                Log.d(TAG, "Image picker success");
            }

        });
    }


}
