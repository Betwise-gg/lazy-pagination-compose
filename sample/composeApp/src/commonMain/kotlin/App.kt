import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.github.ahmad_hamwi.compose.pagination.PaginatedLazyColumn
import io.github.ahmad_hamwi.compose.pagination.rememberPaginationState
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        Scaffold(
            topBar = { TopBar() }
        ) {
            Content(
                modifier = Modifier.padding(it)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    Column {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    "Lazy Pagination - Compose Multiplatform",
                    fontWeight = FontWeight.Bold
                )
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors()
                .copy(containerColor = MaterialTheme.colors.surface)
        )
        HorizontalDivider()
    }
}

@Composable
fun Content(modifier: Modifier = Modifier) {
    val paginationState = rememberPaginationState(
        onRequestPage = { pageNumber: Int ->
            try {
                val page = DataSource.getPage(pageNumber)

                appendPage(
                    items = page.items,
                    isLastPage = page.isLastPage
                )
            } catch (e: Exception) {
                setError(e)
            }
        }
    )

    PaginatedLazyColumn(
        modifier = modifier,
        paginationState = paginationState,
        firstPageProgressIndicator = { FirstPageProgressIndicator() },
        newPageProgressIndicator = { NewPageProgressIndicator() },
        firstPageErrorIndicator = { e ->
            FirstPageErrorIndicator(
                exception = e,
                onRetryClicked = {
                    paginationState.retryLastFailedRequest()
                }
            )
        },
        newPageErrorIndicator = { e ->
            NewPageErrorIndicator(
                exception = e,
                onRetryClicked = {
                    paginationState.retryLastFailedRequest()
                }
            )
        },
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        itemsIndexed(
            paginationState.allItems,
        ) { _, item ->
            Item(value = item)
        }
    }
}
