package my.projects.seasonalanimetracker.db.data.studios

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "show_studio",
    foreignKeys =  [
        ForeignKey(
            entity = DBStudio::class,
            parentColumns = ["id"],
            childColumns = ["studioId"]
        )
    ]
)
data class DBMediaStudio(
    @PrimaryKey val id: Long,
    val mediaId: Long,
    val studioId: Long
)