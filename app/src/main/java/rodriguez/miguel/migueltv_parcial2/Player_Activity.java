package rodriguez.miguel.migueltv_parcial2;

import androidx.appcompat.app.AppCompatActivity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;;
import android.widget.VideoView;
import android.widget.Button;
import android.content.Intent;
import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


public class Player_Activity extends AppCompatActivity {

    private static final int REQUEST_CAMERA_PERMISSION = 100;
    private static final int REQUEST_IMAGE_CAPTURE = 101;

    private VideoView videoView;
    private Button playButton;
    private Button pauseButton;
    private Button menuButton;
    private ImageView userPhoto;
    private TextView userInfo;
    private int videoResId;

    private String userName;
    private int userAge;
    private String userGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        videoView = findViewById(R.id.videoView);
        playButton = findViewById(R.id.playButton);
        pauseButton = findViewById(R.id.pauseButton);
        menuButton = findViewById(R.id.menuButton);
        userPhoto = findViewById(R.id.userPhoto);
        userInfo = findViewById(R.id.userInfo);

        videoResId = getIntent().getIntExtra("VIDEO_RES_ID", -1);
        userName = getIntent().getStringExtra("USER_NAME");
        userAge = getIntent().getIntExtra("USER_AGE", -1);
        userGender = getIntent().getStringExtra("USER_GENDER");

        if (videoResId != -1) {
            Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + videoResId);
            videoView.setVideoURI(videoUri);
        }

        showTakePhotoDialog();

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!videoView.isPlaying()) {
                    videoView.start();
                }
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (videoView.isPlaying()) {
                    videoView.pause();
                }
            }
        });

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("USER_NAME", userName);
                resultIntent.putExtra("USER_AGE", userAge);
                resultIntent.putExtra("USER_GENDER", userGender);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });



        displayUserInfo();
    }

    private void displayUserInfo() {
        String userInfoText = "Nombre: " + userName + "\nEdad: " + userAge + "\nGénero: " + userGender;
        userInfo.setText(userInfoText);
        userInfo.setVisibility(View.VISIBLE);
    }

    private void showTakePhotoDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Tomar foto");
        builder.setMessage("Para poder ver el video, es necesario que pongas una foto de perfil.");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                requestCameraPermission();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Player_Activity.this, Menu_Activity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    private void requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        } else {
            openCamera();
        }
    }

    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            userPhoto.setImageBitmap(imageBitmap);
            userPhoto.setVisibility(View.VISIBLE);
        } else {
            Intent intent = new Intent(Player_Activity.this, Menu_Activity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Intent intent = new Intent(Player_Activity.this, Menu_Activity.class);
                startActivity(intent);
                finish();
            }
        }
    }
}