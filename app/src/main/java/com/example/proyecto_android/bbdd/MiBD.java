package com.example.proyecto_android.bbdd;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.proyecto_android.dao.UsuarioDAO;
import com.example.proyecto_android.model.Monumento;

import java.io.Serializable;
import java.util.ArrayList;

public class MiBD extends SQLiteOpenHelper implements Serializable {

    private static SQLiteDatabase db;
    //nombre de la base de datos
    private static final String database = "VisitamApp";
    //versión de la base de datos
    private static final int version = 17;
    //Instrucción SQL para crear la tabla de USuarios
    private String sqlCreacionUsuarios = "CREATE TABLE usuarios ( id INTEGER PRIMARY KEY AUTOINCREMENT, nif STRING, nombre STRING, " +
            "apellidos STRING, claveSeguridad STRING, email STRING);";
    private String sqlCreacionMonumentos = "CREATE TABLE monumentos ( _id INTEGER PRIMARY KEY, nombre STRING, numPol STRING, " +
            "codVia INTEGER, telefono STRING, ruta INTEGER, latitud FLOAT, longitud FLOAT, imagen STRING);";
    private String sqlCreacionFavoritos = "CREATE TABLE favoritos ( _id INTEGER PRIMARY KEY AUTOINCREMENT,  idUsuario INTEGER, idMonumento INTEGER, " +
            "CONSTRAINT fk_idUsuario FOREIGN KEY (idUsuario) REFERENCES usuarios(id)," +
            "CONSTRAINT fk_idMonumento FOREIGN KEY (idMonumento) REFERENCES monumentos(_id)" +
            ");";


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
    public MiBD(Context context) {
        super( context, database, null, version );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreacionUsuarios);
        db.execSQL(sqlCreacionMonumentos);
        db.execSQL(sqlCreacionFavoritos);

        insercionDatos(db);
        Log.i("SQLite", "Se crea la base de datos " + database + " version " + version);
        upgrade_12(db);
    }

    @Override
    public void onUpgrade( SQLiteDatabase db,  int oldVersion, int newVersion ) {
        Log.i("SQLite", "Control de versiones: Old Version=" + oldVersion + " New Version= " + newVersion  );
        if ( newVersion > oldVersion )
        {
            //elimina tabla
            db.execSQL( "DROP TABLE IF EXISTS usuarios" );
            db.execSQL("DROP TABLE IF EXISTS monumentos");
            db.execSQL("DROP TABLE IF EXISTS favoritos");
            db.execSQL(sqlCreacionUsuarios);
            db.execSQL(sqlCreacionMonumentos);
            db.execSQL(sqlCreacionFavoritos);


            insercionDatos(db);
            Log.i("SQLite", "Se actualiza versión de la base de datos, New version= " + newVersion  );
        }

        if (oldVersion < 12) {
            upgrade_12(db);
        }
    }

    public void upgrade_12(SQLiteDatabase db){
        db.execSQL("ALTER TABLE usuarios ADD is_admin BOOLEAN NOT NULL DEFAULT 'false'");
        Log.i(this.getClass().toString(), "Actualización versión 12 finalizada");

        db.execSQL("INSERT INTO usuarios(id, nif, nombre, apellidos, claveSeguridad, email, is_admin) VALUES (0, '0', 'Héctor', 'Crespo', '1234', 'hector.pi@tia.es', 'true');");
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
        // Insertamos los usuarios
        db.execSQL("INSERT INTO usuarios(id, nif, nombre, apellidos, claveSeguridad, email) VALUES (1, '11111111A', 'Héctor', 'Crespo', '1234', 'hector@hector.es');");

        /*insertamos los monumentos
        db.execSQL("INSERT INTO monumentos(_id, nombre, numPol, codVia, telefono, ruta, coordenadas) VALUES (004165, " +
                "'PALACIO DE LOS MARQUESES DE MALFERIT,O, DE LOS CONDES DE BRIZUELA', " +
                "22, " +
                "12660, " +
                "0, " +
                "5, " +
                "725514.652338833780959);");*/

    }
    public void insercionMonumento(Monumento m){
        String sqlselect = "SELECT * FROM monumentos where _id = "+m.getIdNotes();
        Cursor c = db.rawQuery(sqlselect,null);
        if (c.getCount() < 1) {
            String sql = "INSERT INTO monumentos (_id, nombre, numpol, codVia, telefono, ruta, latitud, longitud, imagen) VALUES ("+m.getIdNotes()+", '"+m.getName()+"', '" +
                    m.getNumPol()+"', "+m.getCodVia()+", '"+m.getTelefono()+"', "+m.getRuta()+","+m.getLatitud()+", "+m.getLongitud()+",'" +m.getImagen()+"');";
            Log.i("DB_INSERT", sql);
            db.execSQL(sql);
        }
        Log.i("DB_INSERT", "El monumento"+m.getIdNotes()+" ya existe");

    }

    public void insercionImgMonumento(Monumento m){
        String sqlselect = "SELECT * FROM monumentos where _id = "+m.getIdNotes();
        Cursor c = db.rawQuery(sqlselect,null);
        if (c.getCount() > 0) {
            String sql = "UPDATE monumentos SET imagen = '"+m.getImagen()+"' WHERE _id = "+m.getIdNotes()+";";
            Log.i("DB_INSERT", sql);
            db.rawQuery(sql, null);
        }
        else  {
            Log.i("DB_INSERT", "El monumento"+m.getIdNotes()+" NO existe");
        }


    }

    public ArrayList<Monumento> recuperarMonumentos(){
        ArrayList<Monumento> monumentos = new ArrayList<>();
        String sql = "SELECT * FROM monumentos;";
        Log.i("DB_SELECT", sql);
        Cursor c = db.rawQuery(sql,null);
        if (c.getCount() > 0) {
            if (c.moveToFirst()){
                do {
                    // Passing values
                    int _id = c.getInt(0);
                    String nombre = c.getString(1);
                    String numPol = c.getString(2);
                    int codVia = c.getInt(3);
                    String telefono = c.getString(4);
                    int ruta = c.getInt(5);
                    float latitud = c.getFloat(6);
                    float longitud = c.getFloat(7);
                    String imagen = c.getString(8);
                    Monumento m =new Monumento(_id, nombre, numPol,codVia,telefono,ruta,latitud,longitud,imagen);
                    monumentos.add(m);
                } while(c.moveToNext());
            }
        }
        return  monumentos;
    }
}