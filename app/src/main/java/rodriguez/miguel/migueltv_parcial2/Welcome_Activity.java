package rodriguez.miguel.migueltv_parcial2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AlertDialog;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


import android.net.Uri;

import android.widget.VideoView;



public class Welcome_Activity extends AppCompatActivity {

    private EditText inputName, inputAge, inputGender;
    private Button btnGoToMenu, btnChangeUser;
    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        btnGoToMenu = findViewById(R.id.btnGoToMenu);
        btnChangeUser = findViewById(R.id.btnChangeUser);
        videoView = findViewById(R.id.videoView);

        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.bienvenida;
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);
        videoView.start();

        btnGoToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputDialog();
            }
        });

        btnChangeUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Welcome_Activity.this, LIST_activity.class);
                startActivity(intent);
            }
        });
    }

    private void showInputDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_user_info, null);
        inputName = dialogView.findViewById(R.id.inputNamee);
        inputAge = dialogView.findViewById(R.id.inputAgee);
        inputGender = dialogView.findViewById(R.id.inputGenderr);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView)
                .setTitle("Introduce tus datos")
                .setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = inputName.getText().toString();
                        String ageStr = inputAge.getText().toString();
                        String gender = inputGender.getText().toString();

                        if (name.isEmpty() || ageStr.isEmpty() || gender.isEmpty()) {
                            showErrorDialog("Por favor completa todos los campos.");
                        } else {
                            int age = Integer.parseInt(ageStr);
                            if (age < 6 || age > 80) {
                                showErrorDialog("La edad debe estar entre 6 y 80 años.");
                            } else {
                                saveUserData(name, age, gender);
                                goToMenuActivity(name, age, gender);
                            }
                        }
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
    }

    private void showErrorDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(message)
                .setPositiveButton("Ok", null)
                .create().show();
    }

    private void saveUserData(String name, int age, String gender) {
        String data = name + "," + age + "," + gender + "\n";
        try {
            FileOutputStream fos = openFileOutput("userdata.txt", MODE_APPEND);
            fos.write(data.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void goToMenuActivity(String name, int age, String gender) {
        Intent intent = new Intent(Welcome_Activity.this, Menu_Activity.class);
        intent.putExtra("USER_NAME", name);
        intent.putExtra("USER_AGE", age);
        intent.putExtra("USER_GENDER", gender);
        startActivity(intent);
        finish();
    }

    private List<String> readUserData() {
        List<String> userList = new ArrayList<>();
        try {
            FileInputStream fis = openFileInput("userdata.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String line;
            while ((line = reader.readLine()) != null) {
                userList.add(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userList;
    }
}