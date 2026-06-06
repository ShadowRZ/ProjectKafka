{ ... }:
{
  projectRootFile = "flake.nix";

  programs = {
    ktfmt.enable = true;
    nixfmt.enable = true;
    taplo.enable = true;
  };
}
