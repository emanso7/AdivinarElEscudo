package org.esei.dm.adivinarelescudo;

        import android.content.res.AssetManager;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.ImageView;

        import android.widget.Toast;

        import androidx.activity.EdgeToEdge;
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

    private String resp1, resp2, resp3,resp4,respuestaCorrecta;
    private QuestionFacade questionFacade;

    private int questionid=1;
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
        int puntuacionTest=0;

        cargaPregunta(questionid,puntuacionTest);


    }

    //funcion que mete los textos en los botones
    private void cargaPregunta(int id,int puntuacion) {

        if (id > 10) {
            Toast.makeText(this, "¡Has terminado la prueba!", Toast.LENGTH_LONG).show();
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

        jugar(question,puntuacion);


    }

    private void jugar(Question question,int puntuacionTest) {
        btnOption1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String op1= question.getOption1();
                validar(op1,puntuacionTest);
            }
        });
        btnOption2.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String op2 = question.getOption2();
                validar(op2,puntuacionTest);

            }
        });
        btnOption3.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String op3= question.getOption3();
                validar(op3,puntuacionTest);
            }
        });
        btnOption4.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String op4= question.getOption4();
                validar(op4,puntuacionTest);

            }
        });
    }

    //funcion validar respuesta
    public void validar(String resp,int puntuacionTest){
        String puntos;
        if(resp.equals(respuestaCorrecta)){
            Toast.makeText(Prueba.this, "RESPUESTA CORRECTA", Toast.LENGTH_SHORT).show();
            puntuacionTest+=5;
            puntos = puntuacionTest+"";
            Toast.makeText(this,"Tienes "+puntos,Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(Prueba.this, "RESPUESTA INCORRECTA", Toast.LENGTH_SHORT).show();
            puntuacionTest-=5;
            puntos = puntuacionTest+"";
            Toast.makeText(this,"Tienes "+puntos,Toast.LENGTH_SHORT).show();
        }
        questionid++;
       // return puntuacionTest;
        cargaPregunta(questionid,puntuacionTest);
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
}