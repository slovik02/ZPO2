package MainActivity
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.chad.R
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Calendar
import android.content.Context
import android.os.Build
import android.text.TextUtils
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import com.example.chad.Notification
import com.example.chad.databinding.ActivityAddmedBinding
import com.google.firebase.auth.FirebaseAuth
import java.util.Date

class addmed : AppCompatActivity() {
    private lateinit var binding: ActivityAddmedBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddmedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createNotificationChannel()
        binding.submitButton.setOnClickListener {
            validateAndSaveDetails()
        }

        // Setting up the spinners
        val timeAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.time,
            android.R.layout.simple_spinner_item
        )
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.timeSpin.adapter = timeAdapter
    }

    @SuppressLint("ScheduleExactAlarm")
    private fun scheduleNotification() {
        val intent = Intent(applicationContext, Notification::class.java)
        val title = "ChAD"
        val message = "Time to take your meds!"
        intent.putExtra("title", title)
        intent.putExtra("message", message)
        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            200,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val time = getTime()
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time,
            pendingIntent
        )
        showAlert(time, title, message)
    }

    private fun validateAndSaveDetails() {
        val medName = binding.entername.text.toString().trim()
        val dosageText = binding.enterdosage.text.toString().trim()
        val unit = binding.dosageSpin.selectedItem.toString()
        val time = binding.timeSpin.selectedItem.toString()

        if (TextUtils.isEmpty(medName)) {
            binding.entername.error = "Please enter a medication name"
            return
        }

        if (TextUtils.isEmpty(dosageText)) {
            binding.enterdosage.error = "Please enter a dosage"
            return
        }

        val dosage = try {
            dosageText.toDouble()
        } catch (e: NumberFormatException) {
            binding.enterdosage.error = "Please enter a valid dosage"
            return
        }

        saveToFirestore(medName, dosage, unit, time)
        scheduleNotification()

        // Navigate to medsMain activity
        navigateToMedsMain()
    }

    private fun saveToFirestore(name: String, dosage: Double, unit: String, time: String) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val userEmail = FirebaseAuth.getInstance().currentUser?.email

        if (userId == null || userEmail == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
            return
        }

        val medication = hashMapOf(
            "name" to name,
            "dosage" to dosage,
            "unit" to unit,
            "time" to time
        )

        val db = FirebaseFirestore.getInstance()
        db.collection("users").document(userEmail)
            .collection("medications").add(medication)
            .addOnSuccessListener {
                Toast.makeText(this, "Medication added to Firestore", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error adding medication to Firestore: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }



    private fun showAlert(time: Long, title: String, message: String) {
        val date = Date(time)
        val dateFormat = android.text.format.DateFormat.getLongDateFormat(applicationContext)
        val timeFormat = android.text.format.DateFormat.getTimeFormat(applicationContext)

        AlertDialog.Builder(this)
            .setTitle("Notification Scheduled")
            .setMessage(
                "Title: $title\nMessage: $message\nAt: ${dateFormat.format(date)} ${timeFormat.format(date)}"
            )
            .setPositiveButton("Okay") { _, _ -> }

    }

    private fun getTime(): Long {
        val calendar = Calendar.getInstance()
        calendar.set(
            binding.datePicker.year,
            binding.datePicker.month,
            binding.datePicker.dayOfMonth,
            binding.timePicker.hour,
            binding.timePicker.minute,
            0
        )

        // Ensure the time is set for the future, not in the past
        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        return calendar.timeInMillis
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val name = "Notif Channel"
        val desc = "A Description of the Channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("ChannelId", name, importance)
        channel.description = desc
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
    }

    private fun navigateToMedsMain() {
        val intent = Intent(this, medsMain::class.java)
        startActivity(intent)
        finish() // Optional: Call finish() to close current activity if not needed anymore
    }
}