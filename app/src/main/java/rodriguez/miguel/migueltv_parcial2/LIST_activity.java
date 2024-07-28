package rodriguez.miguel.migueltv_parcial2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class LIST_activity extends AppCompatActivity {

    private ListView userListView;
    private ArrayAdapter<String> adapter;
    private List<String> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        userListView = findViewById(R.id.userListView);
        users = readUserData();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, users);
        userListView.setAdapter(adapter);

        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedUser = users.get(position);
                String[] userData = selectedUser.split(",");
                String name = userData[0];
                int age = Integer.parseInt(userData[1]);
                String gender = userData[2];

                Intent intent = new Intent(LIST_activity.this, Menu_Activity.class);
                intent.putExtra("USER_NAME", name);
                intent.putExtra("USER_AGE", age);
                intent.putExtra("USER_GENDER", gender);
                startActivity(intent);
                finish();
            }
        });

        userListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String userToRemove = users.get(position);
                users.remove(position);
                adapter.notifyDataSetChanged();
                deleteUserData(userToRemove);
                Toast.makeText(LIST_activity.this, "Usuario eliminado", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }

    private void deleteUserData(String userToRemove) {
        try {
            List<String> userList = new ArrayList<>();
            FileInputStream fis = openFileInput("userdata.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.equals(userToRemove)) {
                    userList.add(line);
                }
            }
            reader.close();

            FileOutputStream fos = openFileOutput("userdata.txt", MODE_PRIVATE);
            for (String user : userList) {
                fos.write((user + "\n").getBytes());
            }
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}