{ ... }:
{
  projectRootFile = "flake.nix";

  programs = {
    ktlint.enable = true;
    nixfmt.enable = true;
    taplo.enable = true;
    yamlfmt.enable = true;
  };
}
