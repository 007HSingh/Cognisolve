{
  description = "Cognisolve development environment";

  inputs.nixpkgs.url = "github:NixOS/nixpkgs/nixos-unstable";

  outputs =
    { self, nixpkgs }:
    let
      system = "x86_64-linux";
      pkgs = import nixpkgs { inherit system; };
    in
    {
      devShells.x86_64-linux.default = pkgs.mkShell {
        buildInputs = with pkgs; [
          python314
          python314Packages.fastapi
          python314Packages.uvicorn
          python314Packages.pydantic
          python314Packages.python-dotenv
          python314Packages.openai
          python314Packages.boto3
          python314Packages.boto3-stubs
          python314Packages.pytest
          python314Packages.pytest-asyncio
          python314Packages.httpx

          git
          curl

          nodejs

          jdk21
          gradle
        ];
        shellHook = ''
          echo "Cognisolve's dev environment!"
        '';
      };
    };
}
