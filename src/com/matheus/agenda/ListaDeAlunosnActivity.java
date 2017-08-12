package com.matheus.agenda;

import java.util.List;

import com.matheus.agenda.dao.AlunoDao;
import com.matheus.agenda.modelo.Aluno;

import android.Manifest;
import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Browser;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class ListaDeAlunosnActivity extends Activity {

	private ListView listaAlunos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_de_alunosn);

		listaAlunos = (ListView) findViewById(R.id.lista_Alunos);// recuperando
																	// id
		editaAluno();
		addAluno();
		registerForContextMenu(listaAlunos);// menu de contexto
	}

	@Override
	protected void onResume() {
		super.onResume();
		carregaLista();// atualizando lista
	}

	// busca no banco alunos cadrastado
	private void carregaLista() {
		// buscando alunos do banco de dados
		AlunoDao dao = new AlunoDao(this);
		List<Aluno> alunos = dao.buscaAluno();
		dao.close();

		// adicionando alunos no listWi
		ArrayAdapter<Aluno> adapter = new ArrayAdapter<Aluno>(this, android.R.layout.simple_expandable_list_item_1,
				alunos);
		listaAlunos.setAdapter(adapter);
	}

	// metodo padrao do android para cria o menu
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.lista_de_alunosn, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	//cria menu dentro do contexto
	@Override 
	public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenuInfo menuInfo) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
		Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(info.position);
		
		Ligar(menu,aluno);
		visitaSite(menu,aluno);
		enviaSms(menu,aluno);
		veLocalizacao(menu,aluno);
		menuDeleta(menu,aluno);
		
	}

	// add botao para abri formulario
	private void addAluno() {
		Button novoAluno = (Button) findViewById(R.id.listaAluno_novoAluno);
		novoAluno.setOnClickListener(new OnClickListener() {

			// abrindo outra active
			@Override
			public void onClick(View v) {
				Intent vFormulario = new Intent(ListaDeAlunosnActivity.this, FormularioActivity.class);
				startActivity(vFormulario);

			}
		});
	}

	// leva para edita os dados
	private void editaAluno() {
		listaAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> lista, View item, int position, long id) {
				Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(position);
				Intent vaiProFormulario = new Intent(ListaDeAlunosnActivity.this, FormularioActivity.class);
				vaiProFormulario.putExtra("aluno", aluno);
				startActivity(vaiProFormulario);

			}
		});

	}

	// menu para deletar aluno
	private void menuDeleta(ContextMenu menu, final Aluno aluno) {
		MenuItem deletar = menu.add("Deletar");
		deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem item) {
				AlunoDao dao = new AlunoDao(ListaDeAlunosnActivity.this);
				dao.deletar(aluno);
				dao.close();
				carregaLista();

				Toast.makeText(ListaDeAlunosnActivity.this, " Deletado " + aluno.getNome(), Toast.LENGTH_SHORT).show();
				return false;
			}
		});
	}

	private void visitaSite(ContextMenu menu, Aluno aluno) {
		MenuItem vSite = menu.add("Visitar Site");
		
	
		String site = aluno.getSite();
		if(!site.startsWith("http://")){
			site = "http://"+aluno.getSite();
		}
		
		  Intent intentSite = new Intent(Intent.ACTION_VIEW);
		  intentSite.setData(Uri.parse(site));
		  vSite.setIntent(intentSite);
		
	}

	private void enviaSms(ContextMenu menu,  Aluno aluno){
		 MenuItem vSite = menu.add("Envia SMS");
		 Intent intentSite = new Intent(Intent.ACTION_VIEW);
		  intentSite.setData(Uri.parse("sms:"+aluno.getTelefone()));
		  vSite.setIntent(intentSite);
	}
	
	private void veLocalizacao(ContextMenu menu,  Aluno aluno){
		MenuItem vMapa = menu.add("Ve no mapa");
		Intent intentMapa = new Intent(Intent.ACTION_VIEW);
		intentMapa.setData(Uri.parse("geo:0,0?q="+aluno.getEndereco()));
		vMapa.setIntent(intentMapa);
	}
	
	private void Ligar(ContextMenu menu, final Aluno aluno){
		final MenuItem itemLigar = menu.add("Ligar");
		itemLigar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
			Intent intentLigar = new Intent(Intent.ACTION_CALL);
			intentLigar.setData(Uri.parse("tel:" + aluno.getTelefone()));
			startActivity(intentLigar);

				startActivity(intentLigar);
				return false;
			}
		});
	}
}
