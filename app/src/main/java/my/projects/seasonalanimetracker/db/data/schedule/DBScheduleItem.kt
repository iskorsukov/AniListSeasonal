package my.projects.seasonalanimetracker.db.data.schedule

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import my.projects.seasonalanimetracker.db.data.media.DBMedia

@Entity(tableName = "schedules",
    foreignKeys = [
        ForeignKey(
            entity = DBMedia::class,
            parentColumns = ["id"],
            childColumns = ["mediaId"]
        )
    ]
)
data class DBScheduleItem(
    @PrimaryKey val id: Long,
    val airingAt: Long,
    val episode: Int,
    val mediaId: Long
)