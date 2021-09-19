package github

class GhPullRequestData(
    val toCommit: GhCommitData,
    val fromCommit: GhCommitData,
    val repo: GhRepo,
    val number: Int,
    val diff: String
)
