package info.jianhuang.routinetrainer

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import info.jianhuang.routinetrainer.db.RoutineDbTable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv_main.setHasFixedSize(true) //the size of the cards will be constant

        rv_main.layoutManager = LinearLayoutManager(this)
        rv_main.adapter = RoutinesAdapter(RoutineDbTable(this).readAllRoutines())

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.add_routine) {
            switchTo(CreateRoutinesActivity::class.java)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun switchTo(c: Class<*>) {
        val intent = Intent(this, c)
        startActivity(intent)
    }


}
