package com.matheus.agenda.dao;

import java.util.ArrayList;
import java.util.List;

import com.matheus.agenda.modelo.Aluno;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AlunoDao extends SQLiteOpenHelper{

	public AlunoDao(Context context) {
		super(context, "Agenda", null, 1);
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE Alunos (id INTEGER PRIMARY KEY, nome TEXT NOT NULL,endereco TEXT,telefone TEXT,site TEXT,nota REAL);";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String sql = "DROP TABLE EXISTS Alunos";
		db.execSQL(sql);
		onCreate(db);
		
	}

	public void insere(Aluno aluno) {
			SQLiteDatabase db = getWritableDatabase();
			ContentValues dados =  pegaDadosAlunos(aluno);
			
			db.insert("Alunos", null, dados);
		
	}

	private ContentValues pegaDadosAlunos(Aluno aluno) {
		ContentValues dados = new ContentValues();
		dados.put("nome", aluno.getNome());
		dados.put("endereco", aluno.getEndereco());
		dados.put("telefone", aluno.getTelefone());
		dados.put("site", aluno.getSite());
		dados.put("nota",aluno.getNota());
		return dados;
	}

	public List<Aluno> buscaAluno() {
		String sql = "SELECT * FROM Alunos";
		SQLiteDatabase db = getReadableDatabase();
		 Cursor cursor = db.rawQuery(sql, null);
		 
		 List<Aluno> alunos = new ArrayList<Aluno>();
		 while(cursor.moveToNext()){
			 Aluno aluno = new Aluno();
			aluno.setId(cursor.getLong(cursor.getColumnIndex("id"))); 
			aluno.setNome(cursor.getString(cursor.getColumnIndex("nome")));
			aluno.setEndereco(cursor.getString(cursor.getColumnIndex("endereco")));
			aluno.setTelefone(cursor.getString(cursor.getColumnIndex("telefone")));
			aluno.setSite(cursor.getString(cursor.getColumnIndex("site")));
			aluno.setNota(cursor.getDouble(cursor.getColumnIndex("nota")));
			
			alunos.add(aluno);
		 }
		 cursor.close();
		 
		return alunos;
	}

	public void deletar(Aluno aluno) {
		SQLiteDatabase db = getWritableDatabase();
		String id = String.valueOf(aluno.getId());
		String[] paramento = {id};
	db.delete("Alunos","id=?",paramento);
	}

	public void altera(Aluno aluno) {
		SQLiteDatabase db = getWritableDatabase();
		ContentValues dados =  pegaDadosAlunos(aluno);
		String id = String.valueOf(aluno.getId());
		String[] params = {id};
		db.update("Alunos", dados, "id = ?",params);
		
	}

}
