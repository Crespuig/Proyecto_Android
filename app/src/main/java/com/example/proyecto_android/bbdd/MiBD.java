package com.example.proyecto_android.bbdd;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.proyecto_android.dao.UsuarioDAO;

import java.io.Serializable;

public class MiBD extends SQLiteOpenHelper implements Serializable {

    private static SQLiteDatabase db;
    //nombre de la base de datos
    private static final String database = "Visita'mApp";
    //versión de la base de datos
    private static final int version = 11;
    //Instrucción SQL para crear la tabla de USuarios
    private String sqlCreacionUsuarios = "CREATE TABLE usuarios ( id INTEGER PRIMARY KEY AUTOINCREMENT, nif STRING, nombre STRING, " +
            "apellidos STRING, claveSeguridad STRING, email STRING);";

    //Instruccion SQL para crear la tabla de Cuentas
    /*private String sqlCreacionCuentas = "CREATE TABLE cuentas ( id INTEGER PRIMARY KEY AUTOINCREMENT, banco STRING, sucursal STRING, " +
            "dc STRING, numerocuenta STRING, saldoactual FLOAT, idcliente INTEGER);" ;
    //Instruccion SQL para crear la tabla de movimientos
    private String sqlCreacionMovimientos = "CREATE TABLE movimientos ( id INTEGER PRIMARY KEY AUTOINCREMENT, tipo INTEGER, fechaoperacion LONG," +
            " descripcion STRING, importe FLOAT, idcuentaorigen INTEGER, idcuentadestino INTEGER);";*/


    private static MiBD instance = null;

    private static UsuarioDAO usuarioDAO;

    public UsuarioDAO getUsuarioDAO() {
        return usuarioDAO;
    }

    /*public CuentaDAO getCuentaDAO() {
        return cuentaDAO;
    }

    public MovimientoDAO getMovimientoDAO() {
        return movimientoDAO;
    }*/

    //private static MovimientoDAO movimientoDAO;

    public static MiBD getInstance(Context context) {
        if(instance == null) {
            instance = new MiBD(context);
            db = instance.getWritableDatabase();
            usuarioDAO = new UsuarioDAO();
        }
        return instance;
    }

    public static SQLiteDatabase getDB(){
        return db;
    }
    public static void closeDB(){db.close();};

    /**
     * Constructor de clase
     * */
    protected MiBD(Context context) {
        super( context, database, null, version );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreacionUsuarios);
        /*db.execSQL(sqlCreacionCuentas);
        db.execSQL(sqlCreacionMovimientos);*/

        insercionDatos(db);
        Log.i("SQLite", "Se crea la base de datos " + database + " version " + version);
    }

    @Override
    public void onUpgrade( SQLiteDatabase db,  int oldVersion, int newVersion ) {
        Log.i("SQLite", "Control de versiones: Old Version=" + oldVersion + " New Version= " + newVersion  );
        if ( newVersion > oldVersion )
        {
            //elimina tabla
            db.execSQL( "DROP TABLE IF EXISTS usuaios" );
            /*db.execSQL( "DROP TABLE IF EXISTS cuentas" );
            db.execSQL( "DROP TABLE IF EXISTS movimientos" );*/
            //y luego creamos la nueva tabla
            db.execSQL(sqlCreacionUsuarios);
            /*db.execSQL(sqlCreacionCuentas);
            db.execSQL(sqlCreacionMovimientos);*/

            insercionDatos(db);
            Log.i("SQLite", "Se actualiza versión de la base de datos, New version= " + newVersion  );
        }
    }

    /*public void insercionMovimiento(Movimiento m){
        db.execSQL("INSERT INTO movimientos (rowid, id, tipo, fechaoperacion, descripcion, importe, idcuentaorigen, idcuentadestino) VALUES (null, null, " +
                m.getTipo()+", "+m.getFechaOperacion().getTime()+", '"+m.getDescripcion()+"', "+m.getImporte()+","+m.getCuentaOrigen().getId()+", "+m.getCuentaDestino().getId()+");");
    }
    public void actualizarSaldo(Cuenta c){
        db.execSQL("UPDATE cuentas SET saldoactual= "+c.getSaldoActual()+" WHERE banco='"+c.getBanco()+"' AND sucursal='"+c.getSucursal()+"' AND dc='"+c.getDc()+"' AND numerocuenta='"
                +c.getNumeroCuenta()+"';");
    }
    public boolean existeCuenta(String banco,String sucursal,String dc,String numCuenta){
        String sql="SELECT numerocuenta FROM cuentas WHERE banco='"+banco+"' AND sucursal='"+sucursal+"' AND dc='"+dc+"' AND numerocuenta='"+numCuenta+"';";
        Cursor c = db.rawQuery(sql,null);
        if (c.getCount() > 0) {
            return true;
        }
        return false;
    }*/

    private void insercionDatos(SQLiteDatabase db){
        // Insertamos los clientes
        db.execSQL("INSERT INTO usuarios(id, nif, nombre, apellidos, claveSeguridad, email) VALUES (1, '11111111A', 'Héctor', 'Crespo', '1234', 'hector@hector.es');");

    }

}