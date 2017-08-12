package com.matheus.agenda;

import com.matheus.agenda.dao.AlunoDao;
import com.matheus.agenda.modelo.Aluno;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FormularioActivity extends Activity {

	private FomularioHeap helper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_formulario);
		helper = new FomularioHeap(this);
		
		Intent intent = getIntent();
		Aluno aluno  = (Aluno) intent.getSerializableExtra("aluno");
		if(aluno != null)helper.prencheFormulario(aluno);
		
		/**
		 * da funcao ao botao
		 * 
		 * Button botaoSalvar = (Button) findViewById(R.id.formulario_salvar);
		 * botaoSalvar.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { case R.id.menu_formulario_ok:
		 *           Toast.makeText(FormularioActivity.this, "Salvo",
		 *           Toast.LENGTH_SHORT).show(); finish();
		 * 
		 *           } });
		 **/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.formulario, menu);
	
		return super.onCreateOptionsMenu(menu);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_formulario_ok:
				Aluno aluno = helper.pegaAluno();
				AlunoDao dao = new AlunoDao(this);
				
				
				if(aluno.getId()  > 0) {
					dao.altera(aluno);
				}
				else {
					dao.insere(aluno);}
				
				dao.close();
				Toast.makeText(FormularioActivity.this,"Aluno: "+ aluno.getNome() + " Salvo", Toast.LENGTH_SHORT).show();
				finish();
				break;
			}

		return super.onOptionsItemSelected(item);
	}

}
