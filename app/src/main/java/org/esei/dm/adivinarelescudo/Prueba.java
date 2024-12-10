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

        import android.widget.Toast;

        import androidx.activity.EdgeToEdge;
        import androidx.appcompat.app.AlertDialog;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.core.graphics.Insets;
        import androidx.core.view.ViewCompat;
        import androidx.core.view.WindowInsetsCompat;

        import org.esei.dm.adivinarelescudo.database.Question;
        import org.esei.dm.adivinarelescudo.database.QuestionFacade;

        import java.io.IOException;
        import java.io.InputStream;


public class Prueba extends AppCompatActivity {
    private ImageView imageView;
    private Button btnOption1, btnOption2, btnOption3, btnOption4;

    private String respuestaCorrecta;
    private QuestionFacade questionFacade;

    private int questionidFacil = 1;
    private int finfacil = 10;
    private int questionidMedia = 11;
    private int finMedia = 20;
    private int questionidDificil = 21;
    private int finDificil = 30;

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


        imageView = findViewById(R.id.id_img_escudo);
        btnOption1 = findViewById(R.id.id_op1);
        btnOption2 = findViewById(R.id.id_op2);
        btnOption3 = findViewById(R.id.id_op3);
        btnOption4 = findViewById(R.id.id_op4);

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

        jugar(question, puntuacion,fin,id);
    }


    private void jugar(Question question,int puntuacionTest,int fin,int id) {
        btnOption1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String op1= question.getOption1();
                validar(op1,puntuacionTest,fin,id);
            }
        });
        btnOption2.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String op2 = question.getOption2();
                validar(op2,puntuacionTest,fin,id);

            }
        });
        btnOption3.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String op3= question.getOption3();
                validar(op3,puntuacionTest,fin,id);
            }
        });
        btnOption4.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String op4= question.getOption4();
                validar(op4,puntuacionTest,fin,id);

            }
        });
    }

    //funcion validar respuesta
    public void validar(String resp,int puntuacionTest,int fin,int id){
        if(resp.equals(respuestaCorrecta)){
            puntuacionTest+=5;
           }
        else {
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
                Intent intent = new Intent(Prueba.this, ActividadHome.class);
                startActivity(intent);
                finish();
            }
        });
        builder.create().show();
    }
}