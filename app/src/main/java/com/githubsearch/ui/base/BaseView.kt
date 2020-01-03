package com.githubsearch.ui.base

interface BaseView<M : BaseUiModel> {
  fun render(viewModel: M)
}