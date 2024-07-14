import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.ahmadhamwi.compose.pagination.PaginatedLazyColumn
import io.github.ahmadhamwi.compose.pagination.rememberPaginationState
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        Scaffold {
            ScaffoldContent(
                modifier = Modifier.padding(it)
            )
        }
    }
}

@Composable
fun ScaffoldContent(modifier: Modifier = Modifier) {
    val paginationState = rememberPaginationState(
        onRequestPage = { requestedPageNumber ->
            val page = DataSource.getPage(requestedPageNumber)

            appendPage(
                items = page.items,
                isLastPage = page.isLastPage
            )
        }
    )

    PaginatedLazyColumn(
        modifier = modifier,
        paginationState = paginationState,
        firstPageProgressIndicator = { FirstPageProgressIndicator() },
        newPageProgressIndicator = { NewPageProgressIndicator() },
        firstPageErrorIndicator = { FirstPageErrorIndicator() },
        newPageErrorIndicator = { NewPageErrorIndicator() },
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        itemsIndexed(
            paginationState.allItems,
            key = { i, item -> "$i-$item" },
        ) { i, item ->
            Item(value = item)
        }
    }
}
