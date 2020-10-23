with import <nixpkgs> {};

stdenv.mkDerivation {

  name = "authkit-servlet";

  buildInputs = with pkgs; [
    jetbrains.idea-ultimate
  ];

  shellHook = ''
    gradle cleanIdea idea
    idea-ultimate authkit-servlet.ipr
    exit
  '';
}

