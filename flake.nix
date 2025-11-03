{
  description = "Project Kafka";

  inputs = {
    nixpkgs = {
      url = "github:NixOS/nixpkgs/nixpkgs-unstable";
    };
    flake-utils = {
      url = "github:numtide/flake-utils";
    };
    ## Treefmt
    treefmt-nix = {
      url = "github:numtide/treefmt-nix";
      inputs.nixpkgs.follows = "nixpkgs";
    };
  };

  outputs =
    {
      self,
      nixpkgs,
      flake-utils,
      treefmt-nix,
      ...
    }:
    flake-utils.lib.eachDefaultSystem (
      system:
      let
        pkgs = nixpkgs.legacyPackages.${system};
        treefmt = treefmt-nix.lib.evalModule pkgs ./treefmt.nix;
      in
      {
        devShells = {
          default = pkgs.mkShell {
            packages = [
              pkgs.android-tools
              pkgs.bundletool
              pkgs.maestro

              # Java
              pkgs.javaPackages.compiler.temurin-bin.jdk-21

              # Formatter
              pkgs.taplo
            ];
          };
        };
        # for `nix fmt`
        formatter = treefmt.config.build.wrapper;
        # for `nix flake check`
        checks = {
          formatting = treefmt.config.build.check self;
        };
      }
    );
}
