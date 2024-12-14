package org.esei.dm.adivinarelescudo;

        import android.annotation.SuppressLint;
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

        import org.esei.dm.adivinarelescudo.GameActivities.ActividadFinal;
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

    private final int questionidFacil = 1;
    private final int finfacil = 10;
    private final int questionidMedia = 11;
    private final int finMedia = 20;
    private final int questionidDificil = 21;
    private final int finDificil = 30;
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

        countPoints=findViewById(R.id.puntos_juego);
        imageView = findViewById(R.id.id_img_escudo);
        btnOption1 = findViewById(R.id.button_option1);
        btnOption2 = findViewById(R.id.button_option2);
        btnOption3 = findViewById(R.id.button_option3);
        btnOption4 = findViewById(R.id.button_option4);

        Adivinar app = (Adivinar) getApplication();
        questionFacade = new QuestionFacade(app);
        int puntuacionTest = 0;

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

            countPoints.setText(String.valueOf(puntuacion));

        // Restaurar colores de los botones
        restaurarColoresBotones();

        jugar(question, puntuacion,fin,id);
    }


    private void jugar(Question question,int puntuacionTest,int fin,int id) {
        btnOption1.setOnClickListener( v -> {
                String op1= question.getOption1();
                validar(btnOption1,op1,puntuacionTest,fin,id);
        });
        btnOption2.setOnClickListener( v -> {
                 String op2 = question.getOption2();
                validar(btnOption2,op2,puntuacionTest,fin,id);
        });
        btnOption3.setOnClickListener( v -> {
                String op3= question.getOption3();
                validar(btnOption3,op3,puntuacionTest,fin,id);
        });
        btnOption4.setOnClickListener( v -> {
                String op4= question.getOption4();
                validar(btnOption4,op4,puntuacionTest,fin,id);
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
        String puntos=String.valueOf(puntuacionFinal);
        builder.setTitle(R.string.titulo_dialogo_prueba);
        builder.setMessage(getString(R.string.texto_dialogo_prueba)+" "+puntos+" "+getString(R.string.points));
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Prueba.this, ActividadFinal.class);
                intent.putExtra("puntajeFinal", puntuacionFinal);
                startActivity(intent);
                finish();
            }
        });
        builder.create().show();
    }

    private void restaurarColoresBotones() {
        // Restaurar el color de fondo predeterminado de los botones usando ContextCompat
        int defaultColor = ContextCompat.getColor(this, R.color.default_option);
        btnOption1.setBackgroundColor(defaultColor);
        btnOption2.setBackgroundColor(defaultColor);
        btnOption3.setBackgroundColor(defaultColor);
        btnOption4.setBackgroundColor(defaultColor);
    }
}