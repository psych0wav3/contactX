package com.example.agenda;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText nome, empresa, id, uf;
    String idaux = "";
    int ID_CONTATO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nome = findViewById(R.id.nameInput);
        empresa = findViewById(R.id.corpInput);
        uf = findViewById(R.id.stateInput);
        id = findViewById(R.id.idInput);
    }

    public void saveContact(View view) {
        idaux = id.getText().toString();
        if (idaux.equals("")){
            Toast.makeText(this, "Coloque um codigo!", Toast.LENGTH_SHORT).show();
        }
        else{
            try{
                ID_CONTATO = Integer.parseInt(idaux);
                
                Agenda a = new Agenda();
                
                a.setNome(nome.getText().toString());
                a.setEmpresa(empresa.getText().toString());
                a.setUf(uf.getText().toString());
                
                long achou = findContact();
                
                if (achou > 0){
                    DatabaseHelper helper = new DatabaseHelper(this);
                    helper.updateAgenda(a, ID_CONTATO);

                    Toast.makeText(this, a.getNome()+"foi ALTERADO com sucesso!", Toast.LENGTH_SHORT).show();
                }
                else{
                    DatabaseHelper helper = new DatabaseHelper(this);
                    helper.addAgenda(a);
                    Toast.makeText(this, a.getNome()+"Foi adicionado com sucesso!", Toast.LENGTH_SHORT).show();
                }
                
            }catch (Exception e){
                e.printStackTrace();
                Toast.makeText(this, "Erro ao adcionar!", Toast.LENGTH_SHORT).show();
            }
        }
        
    }

    public void searchContact(View view) {
        findContact();
    }

    public void clearContact(View view) {
        DatabaseHelper helper = new DatabaseHelper(this);
            int i = helper.deleteAgenda();
        Toast.makeText(this, "deletou "+i+" contatos", Toast.LENGTH_SHORT).show();


    }
    
    public long findContact(){
         DatabaseHelper dbh = new DatabaseHelper(this);
         
         idaux = id.getText().toString();
         
         if(idaux.equals("")){
             Toast.makeText(this, "Coloque um codigo", Toast.LENGTH_SHORT).show();
             return 0;
         }
         else{
             ID_CONTATO = Integer.parseInt(idaux);
             Agenda a = dbh.getAgenda(ID_CONTATO);

             if(a.getNome().equals("")){
                 id.setText("");
                 nome.setText("");
                 empresa.setText("");
                 uf.setText("");

                 Toast.makeText(this, "Registro não Localizado!", Toast.LENGTH_SHORT).show();
                 return 0;
             }
             else{
                 try {
                     nome.setText(a.getNome());
                     empresa.setText(a.getEmpresa());
                     uf.setText(a.getUf());
                 }catch (Exception e){
                     Toast.makeText(this, "Erro ao buscar!", Toast.LENGTH_SHORT).show();
                 }
                 return ID_CONTATO;
             }
         }
    }
}