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

	
	private static int VERSAO = 1;
	private static String TABELA = "Alunos";
	private static String DATABASE = "CadastroCaelum";
	private static final String[] COLUNAS = {"id", "foto", "nome", "telefone", "endereco", "site", "nota"};
	
	public AlunoDAO(Context context) {
		super(context, DATABASE, null, VERSAO);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		criaBanco(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String sql = "DROP TABLE IF EXISTS "+ DATABASE;
		db.execSQL(sql);
		criaBanco(db);
	}
	
	public void insere(Aluno aluno){
		
		ContentValues values = new ContentValues();
		
		values.put("nome", aluno.getNome());
		values.put("telefone", aluno.getTelefone());
		values.put("endereco", aluno.getEndereco());
		values.put("site", aluno.getSite());
		values.put("nota", aluno.getNota());
		values.put("foto", aluno.getFoto());
		
		getWritableDatabase().insert(DATABASE, null, values);
		
	}
	
	
	public List<Aluno> getList(){
		List<Aluno> alunos = new ArrayList<Aluno>();
		
		try {
			Cursor cursor = getWritableDatabase().query(DATABASE, COLUNAS, null, null, DATABASE, null, null);
			
			while (cursor.moveToNext()){
				Aluno aluno = new Aluno();
				
				aluno.setId(cursor.getLong(0));
				aluno.setFoto(cursor.getString(1));
				aluno.setNome(cursor.getString(2));
				aluno.setTelefone(cursor.getString(3));
				aluno.setEndereco(cursor.getString(4));
				aluno.setSite(cursor.getString(5));
				aluno.setNota(cursor.getDouble(6));
				
				alunos.add(aluno);
			}
			
			cursor.close();
			
		} catch (Exception sQLiteException) {
			String excessao = sQLiteException.toString();
			Log.i("SQLiteException: =^/ ", excessao);
		}
		
		return alunos;
	}
	
	public void criaBanco(SQLiteDatabase db){
		String sql = "CREATE TABLE "+ TABELA +" (" +
				"id INTEGER PRIMARY KEY," +
				"nome TEXT UNIQUE NOT NULL," +
				"telefone TEXT," +
				"endereco TEXT," +
				"site TEXT," +
				"nota REAL," +
				"foto TEXT);";
		
		db.execSQL(sql);
	}
	
}



