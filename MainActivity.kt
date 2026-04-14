package com.example.spotifyclone

import android.os.Bundle
import java.util.Calendar
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.spotifyclone.ui.theme.SpotifycloneTheme
import java.util.Locale

class MainActivity : ComponentActivity() {
    private val viewModel: PlayerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.initPlayer(this)
        enableEdgeToEdge()
        setContent {
            SpotifycloneTheme(darkTheme = true) {
                var currentViewState by remember { mutableStateOf("login") }
                
                Surface(color = Color.Black, modifier = Modifier.fillMaxSize()) {
                    when (currentViewState) {
                        "login" -> LoginScreen(
                            onLoginSuccess = { currentViewState = "main" },
                            onNavigateToRegister = { currentViewState = "register" }
                        )
                        "register" -> RegisterScreen(
                            onRegisterSuccess = { currentViewState = "main" },
                            onNavigateToLogin = { currentViewState = "login" }
                        )
                        else -> MainScreen(viewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit, onNavigateToRegister: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.MusicNote,
            contentDescription = null,
            tint = Color(0xFF1DB954),
            modifier = Modifier.size(80.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text("Login to Spotify", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.White)
        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email", color = Color.Gray) },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password", color = Color.Gray) },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            )
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = onLoginSuccess,
            modifier = Modifier.fillMaxWidth().height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1DB954))
        ) {
            Text("Log In", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
        TextButton(onClick = onNavigateToRegister) {
            Text("Don't have an account? Register", color = Color.White)
        }
    }
}

@Composable
fun RegisterScreen(onRegisterSuccess: () -> Unit, onNavigateToLogin: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Create Account", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.White)
        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Full Name", color = Color.Gray) },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email", color = Color.Gray) },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password", color = Color.Gray) },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            )
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = onRegisterSuccess,
            modifier = Modifier.fillMaxWidth().height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1DB954))
        ) {
            Text("Register", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
        TextButton(onClick = onNavigateToLogin) {
            Text("Already have an account? Log In", color = Color.White)
        }
    }
}

sealed class Screen(val route: String, val title: String, val icon: ImageVector, val selectedIcon: ImageVector) {
    object Home : Screen("home", "Home", Icons.Outlined.Home, Icons.Filled.Home)
    object Search : Screen("search", "Search", Icons.Outlined.Search, Icons.Filled.Search)
    object Create : Screen("create", "Create", Icons.Outlined.AddCircleOutline, Icons.Filled.AddCircle)
    object Library : Screen("library", "Library", Icons.Outlined.LibraryMusic, Icons.Filled.LibraryMusic)
}

@Composable
fun MainScreen(viewModel: PlayerViewModel) {
    var selectedScreen by remember { mutableStateOf<Screen>(Screen.Home) }
    val currentSong by viewModel.currentSong.collectAsState()
    val isPlaying by viewModel.isPlaying.collectAsState()
    val currentPosition by viewModel.currentPosition.collectAsState()
    val duration by viewModel.duration.collectAsState()

    Scaffold(
        bottomBar = {
            Column(modifier = Modifier.background(Color.Black.copy(alpha = 0.95f))) {
                currentSong?.let { song ->
                    EnhancedBottomPlayer(
                        song = song,
                        isPlaying = isPlaying,
                        currentPosition = currentPosition,
                        duration = duration,
                        onTogglePlay = { viewModel.togglePlayPause() },
                        onForward = { viewModel.seekForward() },
                        onBackward = { viewModel.seekBackward() },
                        onSeek = { viewModel.seekTo(it) }
                    )
                }
                SpotifyBottomNavigation(
                    selectedScreen = selectedScreen,
                    onScreenSelected = { selectedScreen = it }
                )
            }
        },
        containerColor = Color.Black
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFF1C1C1C), Color.Black),
                        startY = 0f,
                        endY = 1000f
                    )
                )
        ) {
            when (selectedScreen) {
                is Screen.Home -> HomeScreen(viewModel)
                is Screen.Search -> SearchScreen(viewModel)
                is Screen.Create -> CreatePlaylistScreen()
                is Screen.Library -> LibraryScreen()
            }
        }
    }
}

@Composable
fun SpotifyBottomNavigation(
    selectedScreen: Screen,
    onScreenSelected: (Screen) -> Unit
) {
    NavigationBar(
        containerColor = Color.Transparent,
        contentColor = Color.White,
        tonalElevation = 0.dp,
        modifier = Modifier.height(80.dp)
    ) {
        val screens = listOf(Screen.Home, Screen.Search, Screen.Create, Screen.Library)
        screens.forEach { screen ->
            val isSelected = selectedScreen == screen
            NavigationBarItem(
                selected = isSelected,
                onClick = { onScreenSelected(screen) },
                label = { Text(screen.title, fontSize = 10.sp) },
                icon = {
                    Icon(
                        imageVector = if (isSelected) screen.selectedIcon else screen.icon,
                        contentDescription = screen.title,
                        modifier = Modifier.size(26.dp)
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    unselectedIconColor = Color.Gray,
                    selectedTextColor = Color.White,
                    unselectedTextColor = Color.Gray,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}

@Composable
fun HomeScreen(viewModel: PlayerViewModel) {
    val context = LocalContext.current
    val songs = remember { getSongs(context) }
    val scrollState = rememberScrollState()

    val greeting = remember {
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        when (hour) {
            in 0..11 -> "Good morning"
            in 12..15 -> "Good afternoon"
            in 16..20 -> "Good evening"
            else -> "Good night"
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(56.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    greeting,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    "Neeraj J",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Medium
                )
            }
            Row {
                Icon(Icons.Default.Notifications, null, tint = Color.White, modifier = Modifier.size(24.dp))
                Spacer(modifier = Modifier.width(16.dp))
                Icon(Icons.Default.History, null, tint = Color.White, modifier = Modifier.size(24.dp))
                Spacer(modifier = Modifier.width(16.dp))
                Icon(Icons.Default.Settings, null, tint = Color.White, modifier = Modifier.size(24.dp))
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Quick Access Grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.height(220.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            userScrollEnabled = false
        ) {
            items(songs.take(6)) { song ->
                QuickAccessCard(song) { viewModel.playSong(song) }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            "Recently Played",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(songs.reversed()) { song ->
                RecentlyPlayedCard(song) { viewModel.playSong(song) }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            "Jump Back In",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            songs.forEach { song ->
                SongItem(song) { viewModel.playSong(song) }
            }
        }
        
        Spacer(modifier = Modifier.height(100.dp))
    }
}

@Composable
fun QuickAccessCard(song: Song, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.1f)),
        shape = RoundedCornerShape(4.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = song.imageRes,
                contentDescription = null,
                modifier = Modifier.size(56.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = song.title,
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 8.dp),
                maxLines = 2
            )
        }
    }
}

@Composable
fun RecentlyPlayedCard(song: Song, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .width(150.dp)
            .clickable { onClick() }
    ) {
        AsyncImage(
            model = song.imageRes,
            contentDescription = null,
            modifier = Modifier
                .size(150.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = song.title,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1
        )
        Text(
            text = "Song • ${song.artist}",
            color = Color.Gray,
            fontSize = 12.sp,
            maxLines = 1
        )
    }
}

@Composable
fun SearchScreen(viewModel: PlayerViewModel) {
    var searchQuery by remember { mutableStateOf("") }
    val context = LocalContext.current
    val allSongs = remember { getSongs(context) }
    val filteredSongs = allSongs.filter { 
        it.title.contains(searchQuery, ignoreCase = true) || 
        it.artist.contains(searchQuery, ignoreCase = true) 
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Text("Search", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
        Spacer(modifier = Modifier.height(16.dp))
        
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp)),
            placeholder = { Text("What do you want to listen to?", color = Color.Gray) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Color.Black) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (searchQuery.isEmpty()) {
            Text("Browse all", color = Color.White, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val categoryItems = listOf(
                    "Hindi" to Color(0xFFE8115B),
                    "Punjabi" to Color(0xFF148A08),
                    "Pop" to Color(0xFFBD2D28),
                    "Rock" to Color(0xFFE91E63),
                    "Lo-Fi" to Color(0xFF503750),
                    "Party" to Color(0xFFD84000)
                )
                items(categoryItems) { (category, color) ->
                    Card(
                        modifier = Modifier
                            .height(100.dp)
                            .fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = color)
                    ) {
                        Box(modifier = Modifier.fillMaxSize().padding(12.dp)) {
                            Text(category, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        }
                    }
                }
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(filteredSongs) { song ->
                    SongItem(song) { viewModel.playSong(song) }
                }
            }
        }
    }
}

@Composable
fun LibraryScreen() {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Spacer(modifier = Modifier.height(40.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(32.dp).clip(CircleShape).background(Color(0xFF1DB954)), contentAlignment = Alignment.Center) {
                Text("V", color = Color.Black, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text("Your Library", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
        Spacer(modifier = Modifier.height(24.dp))
        
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            SuggestionChip(onClick = {}, label = { Text("Playlists") })
            SuggestionChip(onClick = {}, label = { Text("Artists") })
            SuggestionChip(onClick = {}, label = { Text("Albums") })
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.AutoMirrored.Filled.Sort, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Recents", color = Color.White, fontSize = 12.sp)
            }
            Icon(Icons.Default.GridView, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
        }
        
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(Icons.Default.LibraryMusic, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(64.dp))
                Spacer(modifier = Modifier.height(16.dp))
                Text("No playlists yet", color = Color.Gray)
            }
        }
    }
}

@Composable
fun SongItem(song: Song, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = song.imageRes,
            contentDescription = null,
            modifier = Modifier
                .size(56.dp)
                .clip(RoundedCornerShape(4.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                song.title, 
                color = Color.White, 
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                maxLines = 1
            )
            Text(
                song.artist, 
                color = Color.Gray, 
                fontSize = 14.sp,
                maxLines = 1
            )
        }
        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = "More",
            tint = Color.Gray,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun EnhancedBottomPlayer(
    song: Song,
    isPlaying: Boolean,
    currentPosition: Long,
    duration: Long,
    onTogglePlay: () -> Unit,
    onForward: () -> Unit,
    onBackward: () -> Unit,
    onSeek: (Long) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        color = Color(0xFF282828),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = song.imageRes,
                    contentDescription = null,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        song.title, 
                        color = Color.White, 
                        fontSize = 14.sp, 
                        fontWeight = FontWeight.Bold,
                        maxLines = 1
                    )
                    Text(
                        song.artist, 
                        color = Color.Gray, 
                        fontSize = 12.sp,
                        maxLines = 1
                    )
                }
                
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onBackward) {
                        Icon(Icons.Default.Replay10, contentDescription = null, tint = Color.White, modifier = Modifier.size(24.dp))
                    }
                    IconButton(onClick = onTogglePlay) {
                        Icon(
                            imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    IconButton(onClick = onForward) {
                        Icon(Icons.Default.Forward10, contentDescription = null, tint = Color.White, modifier = Modifier.size(24.dp))
                    }
                }
            }
            
            val progress = if (duration > 0) currentPosition.toFloat() / duration.toFloat() else 0f
            
            Slider(
                value = progress,
                onValueChange = { onSeek((it * duration).toLong()) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
                    .padding(horizontal = 12.dp),
                colors = SliderDefaults.colors(
                    thumbColor = Color.White,
                    activeTrackColor = Color.White,
                    inactiveTrackColor = Color.Gray.copy(alpha = 0.3f)
                )
            )
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(formatTime(currentPosition), color = Color.Gray, fontSize = 10.sp)
                Text(formatTime(duration), color = Color.Gray, fontSize = 10.sp)
            }
        }
    }
}

fun formatTime(ms: Long): String {
    val totalSeconds = ms / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
}

fun getSongs(context: android.content.Context): List<Song> {
    return listOf(
        Song("Gehra Hua", "Dhurandhar", "android.resource://${context.packageName}/${R.raw.song1}", R.drawable.gehra_hua),
        Song("Bairan", "Amit Saini Rohtakiya", "android.resource://${context.packageName}/${R.raw.song2}", R.drawable.bairan),
        Song("Dior", "Shubh", "android.resource://${context.packageName}/${R.raw.song3}", R.drawable.dior),
        Song("Cheques", "Shubh", "android.resource://${context.packageName}/${R.raw.song4}", R.drawable.cheques),
        Song("Tu Hi Hai", "Arijit Singh", "android.resource://${context.packageName}/${R.raw.song5}", R.drawable.tu_hi_hai),
        Song("Aari Aari", "Dhurandhar", "android.resource://${context.packageName}/${R.raw.song6}", R.drawable.aari_aari),
        Song("Sitaare", "Ikkis", "android.resource://${context.packageName}/${R.raw.song7}", R.drawable.ikkis)
    )
}

@Composable
fun CreatePlaylistScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(200.dp)
                .background(Color(0xFF282828), RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier.size(64.dp)
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            "Give your playlist a name",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { /* Create Playlist */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black),
            shape = CircleShape
        ) {
            Text("Create Playlist", fontWeight = FontWeight.Bold)
        }
    }
}
