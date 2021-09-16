package github

class PullRequestData(
    val sourceBranch: GhBranchDescriptor,
    val targetBranch: GhBranchDescriptor,
    val repo: GhRepo,
    val number: Int,
    val diff: String
)
