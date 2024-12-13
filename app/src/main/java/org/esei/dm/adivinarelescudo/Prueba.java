package org.esei.dm.adivinarelescudo;

        import android.content.DialogInterface;
        import android.content.Intent;
        import android.content.res.AssetManager;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.ImageView;

        import android.widget.TextView;
        import android.widget.Toast;

        import androidx.activity.EdgeToEdge;
        import androidx.appcompat.app.AlertDialog;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.core.content.ContextCompat;
        import androidx.core.graphics.Insets;
        import androidx.core.view.ViewCompat;
        import androidx.core.view.WindowInsetsCompat;

        import org.esei.dm.adivinarelescudo.HomeActivities.Actividad_home;
        import org.esei.dm.adivinarelescudo.SesionManager.SesionManager;
        import org.esei.dm.adivinarelescudo.database.AppDatabaseManager;
        import org.esei.dm.adivinarelescudo.database.Question;
        import org.esei.dm.adivinarelescudo.database.QuestionFacade;

        import java.io.IOException;
        import java.io.InputStream;


public class Prueba extends AppCompatActivity {
    private ImageView imageView;
    private TextView countPoints;
    private Button btnOption1, btnOption2, btnOption3, btnOption4;

    private String respuestaCorrecta;
    private QuestionFacade questionFacade;

    private int questionidFacil = 1;
    private int finfacil = 10;
    private int questionidMedia = 11;
    private int finMedia = 20;
    private int questionidDificil = 21;
    private int finDificil = 30;
    private String nombreUsuarioActivo;
    AppDatabaseManager database;
    SesionManager sesionManager;
    private AppDatabaseManager userDatabase; // Instancia de UserDatabase
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_prueba);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        userDatabase = new AppDatabaseManager(this);
        userDatabase.open();

        countPoints=findViewById(R.id.puntos_juego);
        imageView = findViewById(R.id.id_img_escudo);
        btnOption1 = findViewById(R.id.button_option1);
        btnOption2 = findViewById(R.id.button_option2);
        btnOption3 = findViewById(R.id.button_option3);
        btnOption4 = findViewById(R.id.button_option4);

        Adivinar app = (Adivinar) getApplication();
        questionFacade = new QuestionFacade(app);
        int puntuacionTest = 0;

        nombreUsuarioActivo = getIntent().getStringExtra("nombre_usuario_activo");
        //recibe dificultad de anterior actividad
        //switch de facil media dificil cargaPreguntadificultad(questionid,puntuacionTest,fin)
        String valorRecibido = getIntent().getStringExtra("clave");
        switch (valorRecibido){
            case "Facil":
                cargapreguntadificultad(questionidFacil,puntuacionTest,finfacil);
                break;

            case "Dificil":
                cargapreguntadificultad(questionidDificil,puntuacionTest,finDificil);
                break;

            case "Media":
                cargapreguntadificultad(questionidMedia,puntuacionTest,finMedia);
                break;
        }


    }
    //funcion que mete los textos en los botones
//funcion que carga las preguntas segun la dificultad(Falta switch que reciba dificultad)
    private void cargapreguntadificultad(int id, int puntuacion, int fin) {
         if(id >fin){
            lanzaDialogo(puntuacion);
            return;
        }

        Question question = questionFacade.getQuestionsById(id);
        respuestaCorrecta=question.getCorrect();
        cargaImagenDeAssets(question.getPhoto());

        // Asignar valores a los botones
            btnOption1.setText(question.getOption1());
            btnOption2.setText(question.getOption2());
            btnOption3.setText(question.getOption3());
            btnOption4.setText(question.getOption4());

            countPoints.setText(puntuacion);

        // Restaurar colores de los botones
        restaurarColoresBotones();


        jugar(question, puntuacion,fin,id);
    }


    private void jugar(Question question,int puntuacionTest,int fin,int id) {
        btnOption1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String op1= question.getOption1();
                validar(btnOption1,op1,puntuacionTest,fin,id);
            }
        });
        btnOption2.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String op2 = question.getOption2();
                validar(btnOption2,op2,puntuacionTest,fin,id);

            }
        });
        btnOption3.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String op3= question.getOption3();
                validar(btnOption3,op3,puntuacionTest,fin,id);
            }
        });
        btnOption4.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String op4= question.getOption4();
                validar(btnOption4,op4,puntuacionTest,fin,id);

            }
        });
    }

    //funcion validar respuesta
    public void validar(Button seleccion,String resp,int puntuacionTest,int fin,int id){
        int correcto = ContextCompat.getColor(this, R.color.correct_option);
        int incorrecto = ContextCompat.getColor(this, R.color.incorrect_option);
        if(resp.equals(respuestaCorrecta)){
            seleccion.setBackgroundColor(correcto); // Pintar de verde
            puntuacionTest+=5;
           }
        else {
            seleccion.setBackgroundColor(incorrecto);
            puntuacionTest-=5;
        }
        id++;
        cargapreguntadificultad(id,puntuacionTest,fin);
    }
    //funcion que carga imagenes en el imageView
    private void cargaImagenDeAssets(String nombre) {
        try {
            // Obtén el AssetManager
            AssetManager assetManager = getAssets();

            // Abre el archivo en los assets
            InputStream inputStream = assetManager.open(nombre);

            // Decodifica el InputStream en un Bitmap
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

            // Configura el Bitmap en el ImageView
            imageView.setImageBitmap(bitmap);

            // Cierra el InputStream
            inputStream.close();
        } catch (IOException e) {
            Toast.makeText(this,"Error Loading image",Toast.LENGTH_SHORT).show();
        }
    }
    private void lanzaDialogo(int puntuacionFinal){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Test Acabado:");
        builder.setMessage("Tu puntuacion es: "+puntuacionFinal+" puntos");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                /*Intent intent = new Intent(Prueba.this, ActividadHome.class);
                startActivity(intent);
                finish();*/
                actualizaPunuacionUsuario(puntuacionFinal);
            }
        });
        builder.create().show();
    }
    public void actualizaPunuacionUsuario(int puntuacionFinal){
        sesionManager = new SesionManager(this);
        // Obtener el nombre del usuario activo
        String nombreUsuario = sesionManager.getNombreUsuario();
        Intent intent = new Intent(Prueba.this, Actividad_home.class);
        int puntosPrevios = database.getUserPoints(nombreUsuario);
        int puntosTotales=puntosPrevios+puntuacionFinal;
        database.updateScore(nombreUsuario, puntosTotales);
        //intent.putExtra("nombre_usuario_activo", nombreUsuarioActivo); // Pasar el usuario activo
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
    private void restaurarColoresBotones() {
        // Restaurar el color de fondo predeterminado de los botones usando ContextCompat
        int defaultColor = ContextCompat.getColor(this, R.color.default_option);
        btnOption1.setBackgroundColor(defaultColor);
        btnOption2.setBackgroundColor(defaultColor);
        btnOption3.setBackgroundColor(defaultColor);
        btnOption4.setBackgroundColor(defaultColor);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Cerrar la base de datos al destruir la actividad
        if (userDatabase != null) {
            userDatabase.close();
        }
    }
}