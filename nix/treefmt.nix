{ ... }:
{
  projectRootFile = "flake.nix";

  programs = {
    nixfmt.enable = true;
    keep-sorted.enable = true;
    taplo.enable = true;
  };
}
