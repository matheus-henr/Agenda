package com.matheus.agenda;

import com.matheus.agenda.modelo.Aluno;

import android.widget.EditText;
import android.widget.RatingBar;

public class FomularioHeap {

	private EditText campoNome;
	private EditText campoEndereco;
	private EditText campoTelefone;
	private EditText campoSite;
	private RatingBar CampoNota;
	
	private Aluno aluno;

	public FomularioHeap(FormularioActivity activity) {
		 campoNome = (EditText) activity.findViewById(R.id.formulario_nome);
		 campoEndereco = (EditText) activity.findViewById(R.id.formulario_endereco);
		 campoTelefone = (EditText) activity.findViewById(R.id.formulario_telefone);
		 campoSite = (EditText) activity.findViewById(R.id.formulario_site);
		 CampoNota = (RatingBar) activity.findViewById(R.id.formulario_nota);
		 
		 aluno = new Aluno();
		

	}

	public Aluno pegaAluno() {
		aluno.setNome(campoNome.getText().toString());
		aluno.setEndereco(campoEndereco.getText().toString());
		aluno.setTelefone(campoTelefone.getText().toString());
		aluno.setSite(campoSite.getText().toString());
		aluno.setNota(Double.valueOf(CampoNota.getProgress()));
		return aluno;
	}

	public void prencheFormulario(Aluno aluno) {
		campoNome.setText(aluno.getNome());
		campoEndereco.setText(aluno.getEndereco());
		campoTelefone.setText(aluno.getTelefone());
		campoSite.setText(aluno.getSite());
		CampoNota.setProgress((int) aluno.getNota());;
		
		this.aluno = aluno;
		
	}
}
