package rodriguez.miguel.migueltv_parcial2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;
public class Menu_Activity extends AppCompatActivity {


    private static final int REQUEST_CODE_PLAYER = 102;

    private TextView welcomeMessage;
    private ImageView categoryImage1;
    private ImageView categoryImage2;
    private ImageView categoryImage3;
    private Button btnRegreso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        welcomeMessage = findViewById(R.id.welcomeMessagee);
        categoryImage1 = findViewById(R.id.categoryImage1);
        categoryImage2 = findViewById(R.id.categoryImage2);
        categoryImage3 = findViewById(R.id.categoryImage3);
        btnRegreso = findViewById(R.id.btnregreso);


        String name = getIntent().getStringExtra("USER_NAME");
        int age = getIntent().getIntExtra("USER_AGE", -1);
        String gender = getIntent().getStringExtra("USER_GENDER");


        if (name != null && age != -1 && gender != null) {
            welcomeMessage.setText("Hola " + name + ", de acuerdo a tu edad: " + age + " las categorías disponibles son:");

            if (age <= 12) {
                categoryImage1.setImageResource(R.drawable.caricatura);
                categoryImage2.setVisibility(View.GONE);
                categoryImage3.setVisibility(View.GONE);
                categoryImage1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startPlayerActivity(R.raw.caricatura_video, name, age, gender);
                    }
                });
            } else if (age >= 13 && age <= 17) {
                categoryImage1.setImageResource(R.drawable.caricatura);
                categoryImage2.setImageResource(R.drawable.accion);
                categoryImage2.setVisibility(View.VISIBLE);
                categoryImage3.setVisibility(View.GONE);
                categoryImage1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startPlayerActivity(R.raw.caricatura_video, name, age, gender);
                    }
                });
                categoryImage2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startPlayerActivity(R.raw.accion_video, name, age, gender);
                    }
                });
            } else {
                categoryImage1.setImageResource(R.drawable.caricatura);
                categoryImage2.setImageResource(R.drawable.accion);
                categoryImage3.setImageResource(R.drawable.terror);
                categoryImage2.setVisibility(View.VISIBLE);
                categoryImage3.setVisibility(View.VISIBLE);
                categoryImage1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startPlayerActivity(R.raw.caricatura_video, name, age, gender);
                    }
                });
                categoryImage2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startPlayerActivity(R.raw.accion_video, name, age, gender);
                    }
                });
                categoryImage3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startPlayerActivity(R.raw.terror_video, name, age, gender);
                    }
                });
            }
        } else {

            welcomeMessage.setText("No se pudieron cargar los datos del usuario.");
        }


        btnRegreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Menu_Activity.this, Welcome_Activity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void startPlayerActivity(int videoResId, String name, int age, String gender) {
        Intent intent = new Intent(this, Player_Activity.class);
        intent.putExtra("VIDEO_RES_ID", videoResId);
        intent.putExtra("USER_NAME", name);
        intent.putExtra("USER_AGE", age);
        intent.putExtra("USER_GENDER", gender);
        startActivityForResult(intent, REQUEST_CODE_PLAYER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PLAYER && resultCode == RESULT_OK) {

            String name = data.getStringExtra("USER_NAME");
            int age = data.getIntExtra("USER_AGE", -1);
            String gender = data.getStringExtra("USER_GENDER");


            if (name != null && age != -1 && gender != null) {
                welcomeMessage.setText("Hola " + name + ", de acuerdo a tu edad: " + age + " las categorías disponibles son:");


                if (age <= 12) {
                    categoryImage1.setImageResource(R.drawable.caricatura);
                    categoryImage2.setVisibility(View.GONE);
                    categoryImage3.setVisibility(View.GONE);
                } else if (age >= 13 && age <= 17) {
                    categoryImage1.setImageResource(R.drawable.caricatura);
                    categoryImage2.setImageResource(R.drawable.accion);
                    categoryImage2.setVisibility(View.VISIBLE);
                    categoryImage3.setVisibility(View.GONE);
                } else {
                    categoryImage1.setImageResource(R.drawable.caricatura);
                    categoryImage2.setImageResource(R.drawable.accion);
                    categoryImage3.setImageResource(R.drawable.terror);
                    categoryImage2.setVisibility(View.VISIBLE);
                    categoryImage3.setVisibility(View.VISIBLE);
                }
            } else {
                welcomeMessage.setText("No se pudieron cargar los datos del usuario.");
            }
        }
    }
}