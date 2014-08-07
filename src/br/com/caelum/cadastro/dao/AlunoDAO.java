package br.com.caelum.cadastro.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import br.com.caelum.modelo.Aluno;

public class AlunoDAO extends SQLiteOpenHelper {

	
	private static final int VERSAO = 1;
	private static final String TABELA = "Alunos";
	private static final String DATABASE = "CadastroCaelum";
	//private static final String[] COLUNAS = {"id", "nome", "telefone", "endereco", "site", "nota", "foto"};
	
	public AlunoDAO(Context context) {
		super(context, DATABASE, null, VERSAO);
		
		Log.i("ALUNO_DAO", "Construtor Chamado.");
		
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		Log.i("ALUNO_DAO", "Chegou no OnCreate...");
		
		String sql = "CREATE TABLE IF NOT EXISTS " + TABELA + "(id INTEGER PRIMARY KEY," +
				"nome TEXT UNIQUE NOT NULL, telefone TEXT, endereco TEXT, " +
				"site TEXT, nota REAL, foto TEXT); ";
		
		Log.i("ALUNO_DAO", sql);
		
		db.execSQL(sql);
		
		Log.i("SQLite das Trevas", "Banco Criado");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String sql = "DROP TABLE IF EXISTS "+ TABELA;
		db.execSQL(sql);
		onCreate(db);
	}
	
	
	
	
	public void alterar(Aluno aluno){
		
		ContentValues values = new ContentValues();
		String[] args = {aluno.getId().toString()};
		
		values.put("nome", aluno.getNome());
		values.put("telefone", aluno.getTelefone());
		values.put("endereco", aluno.getEndereco());
		values.put("site", aluno.getSite());
		values.put("nota", aluno.getNota());
		values.put("foto", aluno.getFoto());
		
		getWritableDatabase().update(TABELA, values, "id=?", args);
		
		Log.i("ALUNO_DAO", "Aluno Alterado");
	}
	
	
	public void insere(Aluno aluno){
		
		ContentValues values = new ContentValues();
		
		values.put("nome", aluno.getNome());
		values.put("telefone", aluno.getTelefone());
		values.put("endereco", aluno.getEndereco());
		values.put("site", aluno.getSite());
		values.put("nota", aluno.getNota());
		values.put("foto", aluno.getFoto());
		
		getWritableDatabase().insert(TABELA, null, values);
		
		Log.i("ALUNO_DAO", "Aluno Inserido");
	}
	
	
	public void exclui(Aluno aluno){
		String args[] = {aluno.getId().toString()};
		getWritableDatabase().delete(TABELA, "id=?", args);
		
		Log.i("ALUNO_DAO", "Aluno Excluido");
	}
	
	public List<Aluno> getList(){
		
		SQLiteDatabase db = getReadableDatabase();
		
		//Cursor c = getReadableDatabase().query(tabela, colunas, null, null, null, null, null);
		Cursor c = db.rawQuery("SELECT * FROM "+ TABELA, null);
		
		List<Aluno> alunos = new ArrayList<Aluno>(); 
		
		while (c.moveToNext()){
			Aluno aluno = new Aluno();
			
			aluno.setId(c.getLong(0));
			aluno.setNome(c.getString(1));
			aluno.setTelefone(c.getString(2));
			aluno.setEndereco(c.getString(3));
			aluno.setSite(c.getString(4));
			aluno.setNota(c.getDouble(5));
			aluno.setFoto(c.getString(6));
			
			alunos.add(aluno);
		}
		c.close();
		
		Log.i("ALUNO_DAO", "Pesquisa Feita e Lista de Alunos devolvida para Activity");
		
		return alunos;
	}
}



