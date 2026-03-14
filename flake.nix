{
  description = "Project Kafka";

  inputs = {
    nixpkgs = {
      url = "github:NixOS/nixpkgs/nixpkgs-unstable";
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
      treefmt-nix,
      ...
    }:
    let
      # From Crane.
      eachSystem =
        systems: f:
        let
          # Merge together the outputs for all systems.
          op =
            attrs: system:
            let
              ret = f system;
              op =
                attrs: key:
                attrs
                // {
                  ${key} = (attrs.${key} or { }) // {
                    ${system} = ret.${key};
                  };
                };
            in
            builtins.foldl' op attrs (builtins.attrNames ret);
        in
        builtins.foldl' op { } systems;
      eachDefaultSystem = eachSystem [
        "x86_64-linux"
        "aarch64-linux"
      ];
    in
    eachDefaultSystem (
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
              pkgs.jre_headless

              # Formatter
              pkgs.taplo

              # Running Python scripts
              pkgs.python3
              pkgs.python3.pkgs.aiodns
              pkgs.python3.pkgs.aiohttp
              pkgs.python3.pkgs.xmltodict
            ];

            env = {
              GRADLE_OPTS = "-Dorg.gradle.jvmargs=-Xmx4096m -XX:MaxMetaspaceSize=1024m -Dfile.encoding=UTF-8 -XX:+HeapDumpOnOutOfMemoryError -XX:+UseG1GC -Dkotlin.daemon.jvm.options=-Xmx4096m";
            };
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
