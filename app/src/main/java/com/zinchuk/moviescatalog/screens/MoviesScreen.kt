package com.zinchuk.moviescatalog.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.zinchuk.moviescatalog.R
import com.zinchuk.moviescatalog.ui.components.pager.MoviesPager

@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun MoviesScreen() {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .safeDrawingPadding(),
    ) {
        TopAppBar(
            title = {
                Text(stringResource(R.string.app_name))
            },
        )
        MoviesPager()
    }
}
