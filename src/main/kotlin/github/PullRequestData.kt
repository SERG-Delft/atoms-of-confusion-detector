package github

class PullRequestData(
    val targetRepoUsername: String,
    val targetBranch: String,
    val sourceRepoUsername: String,
    val sourceBranch: String,
    val repoName: String,
    val patch: String
)
