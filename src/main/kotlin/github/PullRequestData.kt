package github

class PullRequestData(
    val sourceBranch: GhBranchDescriptor,
    val targetBranch: GhBranchDescriptor,
    val repo: GhRepo,
    val patch: String
)
